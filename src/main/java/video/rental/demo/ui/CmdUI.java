package video.rental.demo.ui;

import video.rental.demo.model.Customer;
import video.rental.demo.model.Rating;
import video.rental.demo.model.Rental;
import video.rental.demo.model.video.PriceCode;
import video.rental.demo.model.video.Video;
import video.rental.demo.model.video.VideoType;
import video.rental.demo.repository.Repository;
import video.rental.demo.service.CustomerService;
import video.rental.demo.service.VideoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CmdUI implements VideoRentalUI {
	private Repository repo;
	private VideoService videoService;
	private CustomerService customerService;

	private static Scanner scanner = new Scanner(System.in);

	// NEW CONSTRUCTOR
	public CmdUI(Repository repo, VideoService videoService, CustomerService customerService) {
		this.repo = repo;
		this.videoService = videoService;
		this.customerService = customerService;
	}

	public void start() {
		boolean quit = false;
		while (!quit) {
			int command = getCommand();
			switch (command) {
			case 0:
				quit = true;
				break;
			case 1:
				listCustomers();
				break;
			case 2:
				listVideos();
				break;
			case 3:
				registerUser();
				break;
			case 4:
				registerVideo();
				break;
			case 5:
				rentVideo();
				break;
			case 6:
				returnVideo();
				break;
			case 7:
				getCustomerReport();
				break;
			case 8:
				clearRentals();
				break;
			default:
				break;
			}
		}
		System.out.println("Bye");
	}

	public void clearRentals() {
		System.out.println("Enter customer code: ");
		int customerCode = scanner.nextInt();

		Customer foundCustomer = repo.findCustomerById(customerCode);

		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			System.out.println("Id: " + foundCustomer.getCode() + "\nName: " + foundCustomer.getName() + "\tRentals: "
					+ foundCustomer.getRentals().size());
			for (Rental rental : foundCustomer.getRentals()) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
			}

			List<Rental> rentals = new ArrayList<Rental>();
			foundCustomer.setRentals(rentals);

			repo.saveCustomer(foundCustomer);
		}
	}

	public void returnVideo() {
		System.out.println("Enter customer code: ");
		int customerCode = scanner.nextInt();

		Customer foundCustomer = repo.findCustomerById(customerCode);
		if (foundCustomer == null)
			return;

		System.out.println("Enter video title to return: ");
		String videoTitle = scanner.next();

		List<Rental> customerRentals = foundCustomer.getRentals();

		for (Rental rental : customerRentals) {
			if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
				Video video = rental.returnVideo();
				video.setRented(false);
				repo.saveVideo(video);
				break;
			}
		}

		repo.saveCustomer(foundCustomer);
	}

	public void listVideos() {
		System.out.println("List of videos");

		List<Video> videos = repo.findAllVideos();

		for (Video video : videos) {
			System.out.println(
					"Video type: " + video.getVideoType() + 
					"\tPrice code: " + video.getPriceCode() + 
					"\tRating: " + video.getVideoRating() +
					"\tTitle: " + video.getTitle()
					); 
		}
		System.out.println("End of list");
	}

	public void listCustomers() {
		System.out.println("List of customers");

		List<Customer> customers = repo.findAllCustomers();

		for (Customer customer : customers) {
			System.out.println("ID: " + customer.getCode() + "\nName: " + customer.getName() + "\tRentals: "
					+ customer.getRentals().size());
			for (Rental rental : customer.getRentals()) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
				System.out.println("\tReturn Status: " + rental.getStatus());
			}
		}
		System.out.println("End of list");
	}

	public void getCustomerReport() {
		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		Customer foundCustomer = repo.findCustomerById(code);

		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			String result = CustomerService.getReport(foundCustomer);
			System.out.println(result);
		}
	}

	public void rentVideo() {
		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		Customer foundCustomer = repo.findCustomerById(code);
		if (foundCustomer == null)
			return;

		System.out.println("Enter video title to rent: ");
		String videoTitle = scanner.next();

		Video foundVideo = repo.findVideoByTitle(videoTitle);

		if (foundVideo == null)
			return;

		if (foundVideo.isRented() == true)
			return;

		Boolean status = videoService.rentFor(videoTitle, foundCustomer);
		if (status == true) {
			repo.saveVideo(foundVideo);
			repo.saveCustomer(foundCustomer);
		} else {
			return;
		}
	}

	// NEW METHOD
	public void registerUser() {
		System.out.println("Enter customer name: ");
		String name = scanner.next();

		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		System.out.println("Enter customer birthday: ");
		String dateOfBirth = scanner.next();

		Customer customer = new Customer(code, name, dateOfBirth);
		repo.saveCustomer(customer);
	}

	// NEW METHOD
	public void registerVideo() {
		System.out.println("Enter video title to register: ");
		String title = scanner.next();

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
		VideoType videoType = VideoType.values()[scanner.nextInt()];

		System.out.println("Enter price code( 1 for Regular, 2 for New Release 3 for Children ):");
		PriceCode priceCode = PriceCode.values()[scanner.nextInt()];

		System.out.println("Enter video rating( 1 for 12, 2 for 15, 3 for 18 ):");
		int videoRating = scanner.nextInt();

		Date registeredDate = new Date();
		Rating rating;
		if (videoRating == 1) rating = Rating.TWELVE;
		else if (videoRating == 2) rating = Rating.FIFTEEN;
		else if (videoRating == 3) rating = Rating.EIGHTEEN;
		else throw new IllegalArgumentException("No such rating " + videoRating);

		Video video = new Video(title, videoType, priceCode, rating, registeredDate);
		repo.saveVideo(video);
	}
//
//	public void register(String object) {
//		if (object.equals("customer")) {
//			System.out.println("Enter customer name: ");
//			String name = scanner.next();
//
//			System.out.println("Enter customer code: ");
//			int code = scanner.nextInt();
//
//			System.out.println("Enter customer birthday: ");
//			String dateOfBirth = scanner.next();
//
//			Customer customer = new Customer(code, name, dateOfBirth);
////			saveCustomer(customer);
//			registerUser(customer);
//		} else {
//			System.out.println("Enter video title to register: ");
//			String title = scanner.next();
//
//			System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
//			VideoType videoType = VideoType.values()[scanner.nextInt()];
//
//			System.out.println("Enter price code( 1 for Regular, 2 for New Release 3 for Children ):");
//			PriceCode priceCode = PriceCode.values()[scanner.nextInt()];
//
//			System.out.println("Enter video rating( 1 for 12, 2 for 15, 3 for 18 ):");
//			int videoRating = scanner.nextInt();
//
//			Date registeredDate = new Date();
//			Rating rating;
//			if (videoRating == 1) rating = Rating.TWELVE;
//			else if (videoRating == 2) rating = Rating.FIFTEEN;
//			else if (videoRating == 3) rating = Rating.EIGHTEEN;
//			else throw new IllegalArgumentException("No such rating " + videoRating);
//
//			Video video = new Video(title, videoType, priceCode, rating, registeredDate);
//
////			saveVideo(video);
//			registerVideo(video);
//		}
//	}

	public int getCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt();

		return command;
	}

}
