package tr.com.migros.tracking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import tr.com.migros.tracking.repository.StoreRepository;
import tr.com.migros.tracking.util.Constants;

import java.util.Objects;

@SpringBootTest
public class StoreServiceImplTests {

    @Autowired
    private StoreServiceImpl storeService;

    @SpyBean
    private StoreRepository storeRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(storeRepository);
    }

    @Test
    void testGetAllStores_whenRepoThrowsException_thenShouldThrowException() {
        // given
        Objects.requireNonNull(cacheManager.getCache(Constants.Cache.CACHE_NAME_STORE)).clear();
        Mockito.doThrow(new RuntimeException("test_message")).when(storeRepository).findAll();

        // when
        final var exception = Assertions.assertThrows(RuntimeException.class,
                () -> storeService.getAllStores());

        // then
        Assertions.assertEquals("test_message", exception.getMessage());
    }

    @Test
    void testGetAllStores_whenHappyPath_thenReturnRecords() {
        // given
        Objects.requireNonNull(cacheManager.getCache(Constants.Cache.CACHE_NAME_STORE)).clear();

        // when
        final var stores = storeService.getAllStores();

        // then
        Mockito.verify(storeRepository).findAll();

        Assertions.assertNotNull(stores);
        Assertions.assertEquals(5, stores.size());
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Ataşehir MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Novada MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Beylikdüzü 5M Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Ortaköy MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Caddebostan MMM Migros".equals(store.getName())));
    }

    @Test
    void testGetAllStores_whenSecondCall_thenReturnFromCache() {
        // given
        Objects.requireNonNull(cacheManager.getCache(Constants.Cache.CACHE_NAME_STORE)).clear();

        // when
        storeService.getAllStores();
        final var stores = storeService.getAllStores();

        // then
        Mockito.verify(storeRepository).findAll();

        Assertions.assertNotNull(stores);
        Assertions.assertEquals(5, stores.size());
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Ataşehir MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Novada MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Beylikdüzü 5M Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Ortaköy MMM Migros".equals(store.getName())));
        Assertions.assertTrue(stores.stream().anyMatch(store -> "Caddebostan MMM Migros".equals(store.getName())));
    }
}
