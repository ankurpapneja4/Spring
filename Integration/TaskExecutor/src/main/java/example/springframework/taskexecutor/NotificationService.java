package example.springframework.taskexecutor;

import org.springframework.scheduling.annotation.Async;

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

    public int getSentNotificationCount(){
        return sentNotificationCount.get();
    }
}
