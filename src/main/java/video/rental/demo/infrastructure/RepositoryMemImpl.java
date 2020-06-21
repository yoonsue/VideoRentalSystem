package video.rental.demo.infrastructure;

import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.video.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryMemImpl implements Repository {

    private Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
    private Map<String, Video> videos = new HashMap<String, Video>();

    public Customer findCustomerById(int code) {
        return customers.get(code);
    }

    public Video findVideoByTitle(String title) {
        return videos.get(title);
    }

    public List<Customer> findAllCustomers() {
        return new ArrayList<Customer>(customers.values());
    }

    public List<Video> findAllVideos() {
        return new ArrayList<Video>(videos.values());
    }

    public void saveCustomer(Customer customer) {
        customers.put(customer.getCode(), customer);
    }

    public void saveVideo(Video video) {
        videos.put(video.getTitle(), video);
    }
}
