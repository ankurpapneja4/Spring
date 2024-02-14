package example.springframework.taskscheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static example.springframework.taskscheduler.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig( classes = { TaskSchedulerConfig.class, PromotionsService.class })
class PromotionsServiceTest {

    @Autowired
    PromotionsService promotionsService;

    @Test
    public void repeatAfterFixedDelayTest(){

        waitFor(4000);

        assertEquals(5, promotionsService.getExecutionCount(), "PromotionService Should be executed Periodically, Every 1 second And Total 5 times.");
    }

}