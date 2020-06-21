package video.rental.demo.infrastructure;

import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.video.Video;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryDBImpl implements Repository {

    // JPA EntityManager
    static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

    private static EntityManager getEm() {
        return em;
    }

    /*
     * Database Access private methods
     */
    public Customer findCustomerById(int code) {
        getEm().getTransaction().begin();
        Customer customer = getEm().find(Customer.class, code);
        getEm().getTransaction().commit();
        return customer;
    }

    public Video findVideoByTitle(String title) {
        getEm().getTransaction().begin();
        Video video = getEm().find(Video.class, title);
        getEm().getTransaction().commit();
        return video;
    }

    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = getEm().createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    public List<Video> findAllVideos() {
        TypedQuery<Video> query = getEm().createQuery("SELECT c FROM Video c", Video.class);
        return query.getResultList();
    }

    public void saveCustomer(Customer customer) {
        try {
            getEm().getTransaction().begin();
            getEm().persist(customer);
            getEm().getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
    }

    public void saveVideo(Video video) {
        try {
            getEm().getTransaction().begin();
            getEm().persist(video);
            getEm().getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
        return;
    }
}
