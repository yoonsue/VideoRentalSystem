package video.rental.demo.ui;

import video.rental.demo.repository.Repository;
import video.rental.demo.service.CustomerService;
import video.rental.demo.service.SampleDataGenerator;
import video.rental.demo.service.VideoService;

// NEW CLASS
public class VideoRentalUIController {
    private Repository repository;
    private VideoRentalUI ui;
    private SampleDataGenerator sampleDataGenerator;

    public VideoRentalUIController(Repository repository, String ui) {
        this.repository = repository;
        this.ui = selectUI(ui);
        this.sampleDataGenerator = new SampleDataGenerator(repository);
    }

    public void start() {
        sampleDataGenerator.generateSamples();
        this.ui.start();
    }

    private VideoRentalUI selectUI(String ui) {
        if (ui.equals("cmd")) {
            return new CmdUI(repository);
        } else if (ui.equals("graphicUI")) {
//            return new GraphicUI(repository);
        }
        return null;
    }

    public VideoRentalUI getUi() {
        return ui;
    }
}
