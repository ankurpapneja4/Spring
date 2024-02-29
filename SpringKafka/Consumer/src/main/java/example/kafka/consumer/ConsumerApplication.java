package example.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args){
        SpringApplication.run( ConsumerApplication.class, args); }

    @KafkaListener( topics = { "topic1" } , groupId = "group1")
    public void receive(String message){
        System.out.println("Received New Message - " + message);
    }
}
