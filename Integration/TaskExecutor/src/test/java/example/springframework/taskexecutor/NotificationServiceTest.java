package example.springframework.taskexecutor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static example.springframework.taskexecutor.Utils.waitFor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig( classes = {TaskExecutorConfig.class, NotificationService.class})
public class NotificationServiceTest {


    @Autowired
    NotificationService notificationService;

    @Test
    public void sendNotificationShouldIncrementNotificationCountAfterSleep()
    {
        notificationService.sendNotification();
        notificationService.sendNotification();
        notificationService.sendNotification();
        notificationService.sendNotification();

        // Non Blocking : Should not wait for sendNotification() to Complete Execution
        assertThat(notificationService.getSentNotificationCount()).isEqualTo( 0 );

        waitFor(110);

        // Notification Count Should Be Updated Till Now.
        assertThat(notificationService.getSentNotificationCount()).isEqualTo( 4 );

    }

    @Test
    public void sendNotificationShouldReturnSuccess() throws ExecutionException, InterruptedException {

        Future<String> result = notificationService.sendNotificationAndGetResponse();

        while( ! result.isDone() ) { System.out.println("Waiting For Result ");  waitFor( 20);}

        assertThat( result.get() ).isEqualTo( "SUCCESS" );

    }

}
