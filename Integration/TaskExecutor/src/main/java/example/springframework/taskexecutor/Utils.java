package example.springframework.taskexecutor;

public class Utils {

    public static final void waitFor(long millis){
        try { Thread.sleep(millis); } catch ( InterruptedException exp) { throw new RuntimeException(exp); }
    }

}
