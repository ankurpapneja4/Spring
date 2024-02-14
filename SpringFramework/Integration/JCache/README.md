## Annotation-base Caching In Spring
```
@Cacheable(cacheNames="books", key="#isbn")
public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)

@Cacheable(cacheNames="books", key="#isbn.rawNumber")
public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
```
[Spring Docs](https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html)