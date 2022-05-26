package tr.com.migros.tracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.migros.tracking.controller.dto.LocationDto;
import tr.com.migros.tracking.exception.RecordNotFoundException;
import tr.com.migros.tracking.model.Courier;
import tr.com.migros.tracking.repository.CourierRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final DistanceService distanceService;
    private final EntranceService entranceService;

    /**
     * Updates geolocations and total distance of the courier with given id and creates entrances if needed.
     *
     * @param id          - id of the courier
     * @param locationDto - new geolocation coming from the courier
     * @return - updated courier
     */
    @Override
    @Transactional
    public Courier updateLocation(final long id, final LocationDto locationDto) {
        final var courier = courierRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("courier is not found with given id: " + id);
                    return new RecordNotFoundException("exception.courierNotFound");
                });

        courier.setTotalDistance(calculateTotalDistance(courier, locationDto))
                .setLatestLatitude(locationDto.getLatitude())
                .setLatestLongitude(locationDto.getLongitude());

        entranceService.createEntrance(courier);
        return courierRepository.save(courier);
    }

    /**
     * Calculates the total distance by old and new location of the courier
     *
     * @param courier     - subject courier
     * @param newLocation - new geolocation of the courier
     * @return - new total distance as old distance + distance of old and new location
     */
    private double calculateTotalDistance(final Courier courier, final LocationDto newLocation) {
        final var oldLatitude = courier.getLatestLatitude();
        final var oldLongitude = courier.getLatestLongitude();

        if (checkIfInitialStatus(courier)) {
            return 0;
        } else {
            final var calculatedDistance = distanceService.calculateDistance(oldLatitude,
                    oldLongitude, newLocation.getLatitude(), newLocation.getLongitude());

            return BigDecimal.valueOf(courier.getTotalDistance() + calculatedDistance)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    /**
     * if the courier is on 0,0 point and total distance is 0 meter then the courier is on initial status,
     * change only latitude and longitude.
     *
     * @param courier - to be checked if it is on the initial status
     * @return - true if the courier is on lat:0 lng:0 and total distance0
     */
    private boolean checkIfInitialStatus(final Courier courier) {
        return courier.getLatestLatitude() == 0 && courier.getLatestLongitude() == 0 && courier.getTotalDistance() == 0;
    }

    /**
     * Returns total distance amount of the courier with given id.
     *
     * @param id - the id of the courier
     * @return - total distance that is travelled by the courier
     */
    @Override
    public double getCourierTotalDistance(final long id) {
        return courierRepository.findById(id)
                .map(Courier::getTotalDistance)
                .orElseThrow(() -> {
                    log.debug("courier is not found with given id: " + id);
                    return new RecordNotFoundException("exception.courierNotFound");
                });
    }
}