package example.springframework.jcache.service;

import example.springframework.jcache.model.Book;
import example.springframework.jcache.repository.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


    @Cacheable( cacheNames = "book", key = "#bookId")
    public Book findBook(long bookId) {

        System.out.println("Fetching data from repository for bookId : " + bookId);
        return bookRepository.findById( bookId );
    }
}
