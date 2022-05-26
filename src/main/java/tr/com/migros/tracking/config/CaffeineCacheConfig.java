package tr.com.migros.tracking.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.migros.tracking.util.Constants;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Value("${cache.courier.reentry.duration}")
    private Duration reentryDuration;

    private CaffeineCache buildCache(final String name, final Duration ttl) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl)
                .maximumSize(1000)
                .build());
    }

    @Bean
    public CacheManager cacheManager() {
        final var entranceCache = buildCache(Constants.Cache.CACHE_NAME_ENTRANCE, reentryDuration);
        final var storeCache = buildCache(Constants.Cache.CACHE_NAME_STORE, Constants.Cache.CACHE_TTL_STORE);

        final var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(entranceCache, storeCache));
        return cacheManager;
    }
}
