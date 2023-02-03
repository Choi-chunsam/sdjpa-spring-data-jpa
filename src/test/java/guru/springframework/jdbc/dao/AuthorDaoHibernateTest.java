package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
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
public class AuthorDaoHibernateTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    AuthorDao authorDao;

    @BeforeEach
    void setUp(){
        authorDao = new AuthorDaoHibernate(entityManagerFactory);
    }

    @Test
    void findAllAuthorsByLastName(){
        List<Author> authors = authorDao.findAllByLastName("Smith", PageRequest.of(0,10));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void findAllAuthorsByLastNameSortLastNameDesc(){
        List<Author> authors = authorDao.findAllByLastName("Smith", PageRequest.of(0,10,
                Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }
}
