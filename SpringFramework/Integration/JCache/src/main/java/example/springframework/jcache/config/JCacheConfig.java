package example.springframework.jcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class JCacheConfig {

    @Bean
    @Profile("caffeine")
    @Primary
    public CacheManager cacheManager(){

        System.out.println("Using Caffeine Cache Manager");

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

                // Set Cache Properties
                cacheManager.setCaffeine( Caffeine.newBuilder()
                                                        .expireAfterWrite(30, TimeUnit.MINUTES) );
                // Enable Caching for Mono, Flex and ComputableFuture
                cacheManager.setAsyncCacheMode( true );

        return cacheManager;
    }

    @Bean
    @Profile("!caffeine")
    public CacheManager defaultCacheManager(){

        System.out.println("Using Concurrent Map Cache Manager");

        return new ConcurrentMapCacheManager();
    }
}
