package example.springframework.taskscheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PromotionsService {

    AtomicInteger executionCount;

    public PromotionsService(){
        executionCount = new AtomicInteger(0);
    }

    @Scheduled( fixedDelay = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void repeatAfterFixedDelay(){

        // something that should run periodically
        executionCount.incrementAndGet();
    }

    public int getExecutionCount(){
        return executionCount.get();
    }

}
