package guru.springframework.jdbc.dao;


import guru.springframework.jdbc.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("guru.springframework.jdbc")
public class BookDaoHibernateTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    BookDao bookDao;

    @BeforeEach
    void setUp(){
        bookDao = new BookDaoHibernate(entityManagerFactory);
    }
    @Test
    void findAllBooksPage1_SortByTitle(){
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0,10,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
    @Test
    void findAllBooks(){
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0,10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);

    }

    @Test
    void findBookByTitle(){
        Book findBook = bookDao.findByTitle("Clean Code");
        assertThat(findBook).isNotNull();
    }

}
