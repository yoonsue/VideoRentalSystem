package video.rental.demo;

import video.rental.demo.repository.BasicRepository;
import video.rental.demo.ui.VideoRentalUIController;

public class Main
{
	private static VideoRentalUIController uiContrloller;

	public static void main(String[] args)
	{
		BasicRepository repository = new BasicRepository();
		uiContrloller = new VideoRentalUIController(repository, "cmd");
		uiContrloller.start();
	}
}


