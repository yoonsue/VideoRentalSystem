package video.rental.demo;

import video.rental.demo.application.Interactor;
import video.rental.demo.application.SampleGenerator;
import video.rental.demo.domain.Repository;
import video.rental.demo.infrastructure.RepositoryMemImpl;
import video.rental.demo.presentation.CmdUI;
import video.rental.demo.presentation.GraphicUI;
import video.rental.demo.presentation.VideoRentalUI;

public class Main
{

	public static void main(String[] args)
	{
		Repository repository = new RepositoryMemImpl();
		SampleGenerator sampleDataGenerator = new SampleGenerator(repository);
		sampleDataGenerator.generateSamples();

		Interactor interactor = new Interactor(repository);

		VideoRentalUI ui = null;
		String uiString = "CMD";
		switch (uiString) {
			case "CMD":
				ui = new CmdUI(interactor);
				break;
			case "GRAPHIC":
				ui = new GraphicUI(interactor);
				break;
			default:
				ui = new GraphicUI(interactor);
		}

		ui.start();
	}

}


