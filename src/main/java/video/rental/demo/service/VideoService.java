package video.rental.demo.service;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rental;
import video.rental.demo.domain.video.Video;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class VideoService {
    private Video video;

    public VideoService(Video video) {
        this.video = video;
    }

    // VideoService
    public boolean rentFor(Customer customer) {
        if (!isUnderAge(customer)) {
            video.setRented(true);
            Rental rental = new Rental(video);
            List<Rental> customerRentals = customer.getRentals();
            customerRentals.add(rental);
            customer.setRentals(customerRentals);
            return true;
        }
        return false;
    }

    public boolean isUnderAge(Customer customer) {
        try {
            // calculate customer's age in years and months

            // parse customer date of birth
            Calendar calDateOfBirth = Calendar.getInstance();
            calDateOfBirth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(customer.getDateOfBirth()));

            // get current date
            Calendar calNow = Calendar.getInstance();
            calNow.setTime(new java.util.Date());

            // calculate age different in years and months
            int ageYr = (calNow.get(Calendar.YEAR) - calDateOfBirth.get(Calendar.YEAR));
            int ageMo = (calNow.get(Calendar.MONTH) - calDateOfBirth.get(Calendar.MONTH));

            // decrement age in years if month difference is negative
            if (ageMo < 0) {
                ageYr--;
            }
            int age = ageYr;

            // determine if customer is under legal age for rating
            switch (video.getVideoRating()) {
                case TWELVE:
                    return age < 12;
                case FIFTEEN:
                    return age < 15;
                case EIGHTEEN:
                    return age < 18;
                default:
                    return false;
            }

        } catch (ParseException e) {
            System.out.println("?");
            e.printStackTrace();
        }
        return false;
    }

}
