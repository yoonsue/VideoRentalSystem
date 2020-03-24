package video.rental.demo.repository;

import video.rental.demo.model.Customer;
import video.rental.demo.model.video.Video;

import java.util.List;

public interface Repository {

    Customer findCustomerById(int code);

    Video findVideoByTitle(String title);

    List<Customer> findAllCustomers();

    List<Video> findAllVideos();

    void saveCustomer(Customer customer);

    void saveVideo(Video video);

}
