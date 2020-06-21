package video.rental.demo.application;

import video.rental.demo.domain.Repository;

import video.rental.demo.domain.Rental;

import video.rental.demo.domain.Rating;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.video.PriceCode;
import video.rental.demo.domain.video.Video;

import video.rental.demo.domain.video.VideoType;
import video.rental.demo.service.CustomerService;
import video.rental.demo.service.VideoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Interactor {

    private Repository getRepository() {
        return repository;
    }

    private Repository repository;

    private CustomerService customerService;
    private VideoService videoService;

    public Interactor(Repository repository) {
        super();
        this.repository = repository;
    }

    public String clearRentals(int customerCode) {
        StringBuilder builder = new StringBuilder();

        Customer foundCustomer = getRepository().findCustomerById(customerCode);

        if (foundCustomer == null) {
            builder.append("No customer found\n");
        } else {
            builder.append("Id: " + foundCustomer.getCode() + "\nName: " + foundCustomer.getName() + "\tRentals: "
                    + foundCustomer.getRentals().size()+"\n");
            for (Rental rental : foundCustomer.getRentals()) {
                builder.append("\tTitle: " + rental.getVideo().getTitle() + " \n");
                builder.append("\tPrice Code: " + rental.getVideo().getPriceCode()+"\n");
            }

            List<Rental> rentals = new ArrayList<Rental>();
            foundCustomer.setRentals(rentals);

            getRepository().saveCustomer(foundCustomer);
        }

        return builder.toString();
    }

    public void returnVideo(int customerCode, String videoTitle) {
        Customer foundCustomer = getRepository().findCustomerById(customerCode);
        if (foundCustomer == null)
            return;

        List<Rental> customerRentals = foundCustomer.getRentals();

        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                Video video = rental.returnVideo();
                video.setRented(false);
                getRepository().saveVideo(video);
                break;
            }
        }

        getRepository().saveCustomer(foundCustomer);
    }

    public void rentVideo(int code, String videoTitle) {
        Customer foundCustomer = getRepository().findCustomerById(code);
        if (foundCustomer == null) {
            System.out.println("customer");
            return;}

        Video foundVideo = getRepository().findVideoByTitle(videoTitle);

        if (foundVideo == null){
            System.out.println("video");
            return;}

        if (foundVideo.isRented() == true){
            System.out.println("rent");
            return;}

        videoService = new VideoService(foundVideo);
        Boolean status = videoService.rentFor(foundCustomer);
        if (status == true) {
            getRepository().saveVideo(foundVideo);
            getRepository().saveCustomer(foundCustomer);
        } else {
            return;
        }
    }

    public String listCustomer() {
        StringBuilder builder = new StringBuilder();
        List<Customer> customers = getRepository().findAllCustomers();

        for (Customer customer : customers) {
            builder.append("ID: " + customer.getCode() + "\nName: " + customer.getName() + "\tRentals: "
                    + customer.getRentals().size()+"\n");
            for (Rental rental : customer.getRentals()) {
                builder.append("\tTitle: " + rental.getVideo().getTitle() + " "+"\n");
                builder.append("\tPrice Code: " + rental.getVideo().getPriceCode()+"\n");
                builder.append("\tReturn Status: " + rental.getStatus()+"\n");
            }
        }

        return builder.toString();
    }

    public void registerVideo(String title, VideoType videoType, PriceCode priceCode, int videoRating) {
        Date registeredDate = new Date();
        Rating rating;
        if (videoRating == 1) rating = Rating.TWELVE;
        else if (videoRating == 2) rating = Rating.FIFTEEN;
        else if (videoRating == 3) rating = Rating.EIGHTEEN;
        else throw new IllegalArgumentException("No such rating " + videoRating);

        Video video = new Video(title, videoType, priceCode, rating, registeredDate);

        getRepository().saveVideo(video);
    }

    public void registerCustomer(String name, int code, String dateOfBirth) {
        Customer customer = new Customer(code, name, dateOfBirth);
        getRepository().saveCustomer(customer);
    }

    public Customer findCustomerById(int code) {
        Customer foundCustomer = getRepository().findCustomerById(code);
        return foundCustomer;
    }

    public String listVideos() {
        StringBuilder builder = new StringBuilder();
        List<Video> videos = getRepository().findAllVideos();
        for (Video video : videos) {
            builder.append(
                    "Video type: " + video.getVideoType() +
                            "\tPrice code: " + video.getPriceCode() +
                            "\tRating: " + video.getVideoRating() +
                            "\tTitle: " + video.getTitle() + "\n"
            );
        }
        return builder.toString();
    }

    public String getReport(Customer foundCustomer) {
        customerService = new CustomerService(foundCustomer);
        return customerService.getReport();
    }
}
