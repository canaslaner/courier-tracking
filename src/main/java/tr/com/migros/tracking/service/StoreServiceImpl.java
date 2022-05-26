package tr.com.migros.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.migros.tracking.model.Store;
import tr.com.migros.tracking.repository.StoreRepository;
import tr.com.migros.tracking.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    /**
     * Returns all stores that are defined. It is supported by cache for better performance.
     *
     * @return - stores
     */
    @Override
    @Cacheable(cacheNames = Constants.Cache.CACHE_NAME_STORE)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}