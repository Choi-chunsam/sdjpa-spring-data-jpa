package guru.springframework.jdbc.dao;


import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoJDBCTemplate;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc"})
public class AuthorDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    AuthorDao authorDao;

    @BeforeEach
    void setUp(){
        authorDao = new AuthorDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void findAllAuthorByLastName(){
        List<Author> authors = authorDao.findAllByLastName("Smith",
                PageRequest.of(0,10));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);

    }

    @Test
    void findAllAuthorByLastName_SortByFirstNameDesc(){
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith",PageRequest.of(0,10,
                Sort.by(Sort.Order.desc("first_name"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }
    @Test
    void findAllAuthorByLastName_SortByFirstNameAsc(){
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith",PageRequest.of(0,10,
                Sort.by(Sort.Order.asc("first_name"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void findAllAuthorsByLastNameAllRecs() {
        List<Author> authors = authorDao.findAllByLastName("Smith", PageRequest.of(0, 100));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(40);
    }
}
