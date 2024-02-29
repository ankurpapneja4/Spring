package example.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KafkaProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final AtomicInteger counter;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.counter = new AtomicInteger(0);
    }

    @Scheduled( fixedDelay = 1 , timeUnit = TimeUnit.SECONDS)
    public void send(){

        kafkaTemplate.send( "topic1", "message-"+counter.incrementAndGet() );
    }
}
