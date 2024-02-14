package example.springframework.jcache.repository;

import example.springframework.jcache.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookRepository {
    public Book findById(long bookId ) {

        return new Book( bookId, null);
    }
}
