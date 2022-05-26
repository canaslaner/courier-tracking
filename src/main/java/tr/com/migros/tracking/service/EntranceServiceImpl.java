package tr.com.migros.tracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import tr.com.migros.tracking.model.Courier;
import tr.com.migros.tracking.model.Entrance;
import tr.com.migros.tracking.model.Store;
import tr.com.migros.tracking.repository.EntranceRepository;
import tr.com.migros.tracking.util.Constants;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntranceServiceImpl implements EntranceService {

    private final EntranceRepository entranceRepository;
    private final StoreService storeService;
    private final DistanceService distanceService;
    private final CacheManager cacheManager;

    @Value("${courier.log.radius-in-meters}")
    private int entranceRadiusInMeters;
    @Value("${cache.courier.reentry.duration}")
    private Duration reentryDuration;

    /**
     * Creates entrances if new entrance occurred.
     *
     * @param courier - the subject courier
     */
    @Override
    @Transactional
    public void createEntrance(final Courier courier) {
        final var entrancesToSave = storeService.getAllStores()
                .stream()
                .filter(store -> checkIfNotReentry(courier, store))
                .filter(store -> checkIfEntered(courier, store))
                .map(store -> Entrance.builder()
                        .store(store).courier(courier)
                        .latitude(courier.getLatestLatitude())
                        .longitude(courier.getLatestLongitude()).build())
                .collect(Collectors.toList());

        if (!entrancesToSave.isEmpty()) {
            if (log.isDebugEnabled()) {
                log.debug("New {}  entrances created for give courier:{}", entrancesToSave.size(), courier.getId());
            }

            // to put data in cache saveAll was not preferred, so to get better performance, save all entities in one trx
            entrancesToSave.forEach(entranceRepository::save);
        }
    }

    /**
     * Checks if the courier location is in the circumference of stores.
     *
     * @param courier - the subject courier
     * @param store   - reference location from this store
     * @return - true if entrance occurred
     */
    private boolean checkIfEntered(final Courier courier, final Store store) {
        return distanceService.calculateDistance(courier.getLatestLatitude(), courier.getLatestLongitude(),
                store.getLatitude(), store.getLongitude()) <= entranceRadiusInMeters;
    }

    /**
     * Checks the duration and decides if the entrance is 'reentry' or not.
     *
     * @param courier - the subject courier
     * @param store   - reference location from this store
     * @return - true if not a 'reentry'
     */
    private boolean checkIfNotReentry(final Courier courier, final Store store) {
        final var cacheKey = courier.getId() + "_" + store.getId();
        return Optional.ofNullable(cacheManager.getCache(Constants.Cache.CACHE_NAME_ENTRANCE))
                .map(cache -> cache.get(cacheKey, Entrance.class))
                .filter(entrance -> entrance.getCreatedDate().isAfter(Instant.now().minus(reentryDuration)))
                .isEmpty();
    }

}