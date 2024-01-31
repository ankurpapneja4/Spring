package example.springframework.jcache.service;

import example.springframework.jcache.config.JCacheConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = JCacheConfig.class)
@ActiveProfiles("caffeine")
public class BookServiceTest {

    @Test
    public void contextLoads(){

        System.out.println("Context loads");
    }
}
