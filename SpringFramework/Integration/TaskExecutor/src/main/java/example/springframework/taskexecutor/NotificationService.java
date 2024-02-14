package example.springframework.taskexecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static example.springframework.taskexecutor.Utils.waitFor;

public class NotificationService {

    private final AtomicInteger sentNotificationCount;

    public NotificationService(){
        sentNotificationCount = new AtomicInteger(0);
    }

    @Async
    public void sendNotification(){

        // ToDo : Send Notification To User
        waitFor( 100 );

        //Update Sent Notification Count
        sentNotificationCount.incrementAndGet();
    }

    @Async
    public Future<String> sendNotificationAndGetResponse(){

        // ToDo : Send Notification To User
        waitFor( 100 );

        //Update Sent Notification Count
        return new AsyncResult<>("SUCCESS");

    }

    public int getSentNotificationCount(){
        return sentNotificationCount.get();
    }
}
