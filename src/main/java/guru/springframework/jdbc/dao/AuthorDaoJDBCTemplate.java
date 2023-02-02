package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.hibernate.dialect.JDataStoreDialect;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AuthorDaoJDBCTemplate implements AuthorDao{

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName,Pageable pageable) {
        String sql = "SELECT * FROM author WHERE last_name = ? order by first_name " +
        pageable.getSort().getOrderFor("first_name")
                .getDirection().name() + " limit ? offset ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql,getAuthorMapper(),lastName,pageable.getPageSize(),pageable.getOffset());
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?",getAuthorMapper(),id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? and last_name = ?",getAuthorMapper(),firstName,lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO author(first_name,last_name) VALUES (?,?)",author.getFirstName(),author.getLastName());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID",Long.class);
        return this.getById(createdId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("UPDATE author SET first_name = ?, last_name = ?",author.getFirstName(),author.getLastName());
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    private AuthorMapper getAuthorMapper(){
        return new AuthorMapper();
    }
}
