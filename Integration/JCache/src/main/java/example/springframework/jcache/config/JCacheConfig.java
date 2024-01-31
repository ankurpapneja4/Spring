package example.springframework.jcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class JCacheConfig {

    @Bean
    @Profile("caffeine")
    public CacheManager cacheManager(){

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

                // Set Cache Properties
                cacheManager.setCaffeine( Caffeine.newBuilder()
                                                        .expireAfterWrite(30, TimeUnit.MINUTES) );
                // Enable Caching for Mono, Flex and ComputableFuture
                cacheManager.setAsyncCacheMode( true );

        return cacheManager;
    }
}
