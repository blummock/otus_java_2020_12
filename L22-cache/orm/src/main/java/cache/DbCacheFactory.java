package cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbCacheFactory {

    private static CacheManager cacheManager;
    private static final Logger logger = LoggerFactory.getLogger(DbCacheFactory.class);

    public static <K, V> Cache<K, V> createSimple(Class<K> key, Class<V> value, Integer size) {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        var cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(event ->
                                logger.info("updated key: {}, value: {}", event.getKey(), event.getNewValue()),
                        EventType.CREATED, EventType.UPDATED)
                .ordered().synchronous();

        Cache<K, V> cache = cacheManager.createCache("Demo-Cache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(key, value,
                        ResourcePoolsBuilder.heap(size))
                        .withService(cacheEventListenerConfiguration)
                        .build());

        logger.info("Cache setup is done");
        return cache;
    }

    public static void close() {
        if (cacheManager != null) {
            cacheManager.close();
        }
    }
}
