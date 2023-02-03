package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDaoHibernate implements AuthorDao{

    EntityManagerFactory emf;

    public AuthorDaoHibernate(EntityManagerFactory emf){ this.emf = emf;}
    @Override
    public List<Author> findAllByLastName(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        try{
            String hql = "SELECT a FROM Author a where a.lastName = :lastName ";

            if(pageable.getSort().getOrderFor("firstname") != null){
                hql = hql + " order by a.firstName " + pageable.getSort().getOrderFor("firstname")
                        .getDirection().name();
            }

            TypedQuery<Author> query = em.createQuery(hql, Author.class);
            query.setParameter("lastName",lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }


    }

    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        return null;
    }

    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
            query.setParameter("id",id);
            return query.getSingleResult();
        }finally {
            em.close();
        }



    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {

        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
