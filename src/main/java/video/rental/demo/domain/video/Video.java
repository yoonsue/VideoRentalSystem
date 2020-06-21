package video.rental.demo.domain.video;

import video.rental.demo.domain.Rating;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Table(name = "VIDEO", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Video {
    @Id
    private String title;
    private Rating videoRating;
    private PriceCode priceCode;
    private VideoType videoType;

    private Date registeredDate;
    private boolean rented;

    public Video() {
    }    // for hibernate

    public Video(String title, VideoType videoType, PriceCode priceCode, Rating videoRating, Date registeredDate) {
        this.title = title;
        this.videoType = videoType;
        this.priceCode = priceCode;
        this.priceCode = priceCode;
        this.videoRating = videoRating;
        this.registeredDate = registeredDate;
    }

    public String getTitle() {
        return title;
    }

    public Rating getVideoRating() {
        return videoRating;
    }

    public PriceCode getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(PriceCode priceCode) {
        this.priceCode = priceCode;
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public int getLateReturnPointPenalty() {
        int pentalty = 0;
        switch (this.getVideoType()) {
            case VHS:
                pentalty = 1; break;
            case CD:
                pentalty = 2; break;
            case DVD:
                pentalty = 3; break;
        }
        return pentalty;
    }
}