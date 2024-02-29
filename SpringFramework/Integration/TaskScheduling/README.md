## Annotation Support Scheduled Execution

**Configuration**
```java
@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {
}
```

**Scheduled Task**
```
@Scheduled( fixedRate = 1 , timeUnit = TimeUnit.SECONDS)
public void doSomething(){

    // This Task will be executed every 1 second

}
```
[Spring Docs](https://docs.spring.io/spring-framework/reference/integration/scheduling.html)