## Annotation Support Asynchronous Execution

**Configuration**
```java
@Configuration
@EnableAsync
public class AppConfig {
}
```

**Passing Value**
```
@Async
void doSomething(String s) {
        // this will be run asynchronously
}
```
**Returning Value**
```
@Async
Future<String> returnSomething(int i) {
	// this will be run asynchronously
}
```
[Spring Docs](https://docs.spring.io/spring-framework/reference/integration/scheduling.html)