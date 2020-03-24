package video.rental.demo.service;

import video.rental.demo.model.Customer;
import video.rental.demo.model.Rating;
import video.rental.demo.model.Rental;
import video.rental.demo.model.video.PriceCode;
import video.rental.demo.model.video.Video;
import video.rental.demo.model.video.VideoType;
import video.rental.demo.repository.Repository;

import java.util.Date;
import java.util.List;

public class SampleDataGenerator {
    private Repository repo;

    public SampleDataGenerator(Repository repo) {
        this.repo = repo;
    }

    public void generateSamples() {
        Customer james = new Customer(0, "James", "1975-5-15");
        Customer brown = new Customer(1, "Brown", "2001-3-17");
        repo.saveCustomer(james);
        repo.saveCustomer(brown);

        Video v1 = new Video("V1", VideoType.CD, PriceCode.REGULAR, Rating.FIFTEEN, new Date());
        v1.setRented(true);
        Video v2 = new Video("V2", VideoType.DVD, PriceCode.NEW_RELEASE, Rating.TWELVE, new Date());
        v2.setRented(true);
        repo.saveVideo(v1);
        repo.saveVideo(v2);

        Rental r1 = new Rental(v1);
        Rental r2 = new Rental(v2);

        List<Rental> rentals = james.getRentals();
        rentals.add(r1);
        rentals.add(r2);
        james.setRentals(rentals);
        repo.saveCustomer(james);
    }

}
