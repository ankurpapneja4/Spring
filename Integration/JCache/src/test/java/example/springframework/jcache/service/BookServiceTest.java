package example.springframework.jcache.service;

import example.springframework.jcache.config.JCacheConfig;
import example.springframework.jcache.model.Book;
import example.springframework.jcache.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {JCacheConfig.class, BookServiceTest.TestConfiguration.class})
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
@ActiveProfiles("caffeine")
public class BookServiceTest {

    @Component
    @ComponentScan(basePackageClasses = { BookService.class })
    public static class TestConfiguration{ }

    @MockBean
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Test
    public void contextLoads(){

        System.out.println("Context loads");
    }

    @Test
    public void findBookMethod_ShouldCacheData(){

        // Given
        given(bookRepository.findById(1l)).willReturn( new Book(1, "Spring In Action") );

        // When : Call 3 Times findBook() With Same bookId
        bookService.findBook(1l);
        bookService.findBook(1l);
        bookService.findBook(1l);

        // Then : Only 1 Times Data Should Be Loaded From Repository
        then(bookRepository).should( times(1) ).findById(1l);
    }

    @Test
    public void findBookMethod_WhenDataIsNotCached_ShouldLoadDataFromDBAndUpdateCache(){

        // Given
        given(bookRepository.findById( anyLong() )).willReturn( new Book() );

        // When : Call 9 Times findBook() With 3 Different bookId
        bookService.findBook(1l);
        bookService.findBook(1l);
        bookService.findBook(2l);
        bookService.findBook(2l);
        bookService.findBook(3l);
        bookService.findBook(3l);
        bookService.findBook(1l);
        bookService.findBook(2l);
        bookService.findBook(3l);

        // Then : Only 3 Times Data Should Be Loaded From Repository
        then(bookRepository).should( times(3) ).findById( anyLong() );
    }

    @AfterEach
    public void resetMock() {
        reset( bookRepository );
    }

}
