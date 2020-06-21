package video.rental.demo.presentation;

import video.rental.demo.application.Interactor;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.video.PriceCode;
import video.rental.demo.domain.video.VideoType;

import java.util.Scanner;

public class CmdUI implements VideoRentalUI {

	private static Scanner scanner = new Scanner(System.in);

	private Interactor interactor;

	public CmdUI(Interactor interactor) {
		this.interactor = interactor;
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
				register("customer");
				break;
			case 4:
				register("video");
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

		String result = interactor.clearRentals(customerCode);
		System.out.print(result);
	}

	public void returnVideo() {
		System.out.println("Enter customer code: ");
		int customerCode = scanner.nextInt();

		System.out.println("Enter video title to return: ");
		String videoTitle = scanner.next();

		interactor.returnVideo(customerCode, videoTitle);
	}

	public void listVideos() {
		System.out.println("List of videos");

		String result = interactor.listVideos();
		System.out.print(result);

		System.out.println("End of list");
	}

	public void listCustomers() {
		System.out.println("List of customers");

		String result = interactor.listCustomer();

		System.out.print(result);
		System.out.println("End of list");
	}

	public void getCustomerReport() {
		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		Customer foundCustomer = interactor.findCustomerById(code);

		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			String result = interactor.getReport(foundCustomer);
			System.out.println(result);
		}
	}

	public void rentVideo() {
		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		System.out.println("Enter video title to rent: ");
		String videoTitle = scanner.next();

		interactor.rentVideo(code, videoTitle);
	}

	public void register(String object) {
		if (object.equals("customer")) {
			registerUser();
		} else if (object.equals("video")) {
			registerVideo();
		}
	}

	public void registerUser() {
		System.out.println("Enter customer name: ");
		String name = scanner.next();

		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		System.out.println("Enter customer birthday: ");
		String dateOfBirth = scanner.next();

		interactor.registerCustomer(name, code, dateOfBirth);
	}

	public void registerVideo() {
		System.out.println("Enter video title to register: ");
		String title = scanner.next();

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
		VideoType videoType = VideoType.values()[scanner.nextInt() - 1];

		System.out.println("Enter price code( 1 for Regular, 2 for New Release 3 for Children ):");
		PriceCode priceCode = PriceCode.values()[scanner.nextInt() - 1];

		System.out.println("Enter video rating( 1 for 12, 2 for 15, 3 for 18 ):");
		int videoRating = scanner.nextInt();

		interactor.registerVideo(title, videoType, priceCode, videoRating);
	}

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
