package video.rental.demo.ui;

import video.rental.demo.repository.Repository;
import video.rental.demo.service.CustomerService;
import video.rental.demo.service.SampleDataGenerator;
import video.rental.demo.service.VideoService;

// NEW CLASS
public class VideoRentalUIController {
    private Repository repository;
    private VideoRentalUI ui;
    private VideoService videoService;
    private CustomerService customerService;
    private SampleDataGenerator sampleDataGenerator;

    public VideoRentalUIController(Repository repository, String ui) {
        this.repository = repository;
        this.ui = selectUI(ui);
        this.videoService = new VideoService(repository);
        this.customerService = new CustomerService(repository);
        this.sampleDataGenerator = new SampleDataGenerator(repository);
    }

    public void start() {
        sampleDataGenerator.generateSamples();
        this.ui.start();
    }

    private VideoRentalUI selectUI(String ui) {
        if (ui.equals("cmd")) {
            return new CmdUI(repository, videoService, customerService);
        } else if (ui.equals("graphicUI")) {
//            return new GraphicUI(repository, videoService, customerService);
        }
        return null;
    }

    public VideoRentalUI getUi() {
        return ui;
    }
}
