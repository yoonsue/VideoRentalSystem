package video.rental.demo.repository;

import video.rental.demo.PersistenceManager;
import video.rental.demo.model.Customer;
import video.rental.demo.model.video.Video;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class BasicRepository implements Repository {
    // JPA EntityManager
    private static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

    /*
     * Database Access private methods
     */
    public Customer findCustomerById(int code) {
        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, code);
        em.getTransaction().commit();
        return customer;
    }

    public Video findVideoByTitle(String title) {
        em.getTransaction().begin();
        Video video = em.find(Video.class, title);
        em.getTransaction().commit();
        return video;
    }

    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    public List<Video> findAllVideos() {
        TypedQuery<Video> query = em.createQuery("SELECT c FROM Video c", Video.class);
        return query.getResultList();
    }

    public void saveCustomer(Customer customer) {
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
    }

    public void saveVideo(Video video) {
        try {
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
        return;
    }
}
