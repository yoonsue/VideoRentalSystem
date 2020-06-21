package video.rental.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVideoRentalSystem {

    private GoldenMaster goldenMaster;

    @BeforeEach
    public void init() {
        goldenMaster = new GoldenMaster();
    }

    @Test
    public void testCheckRunResultWithGoldenMaster_Win() {
        // Arrange (Given)
        String expected = goldenMaster.getGoldenMaster();

        // Act (When)
        String actual = goldenMaster.getRunResult();

        // Assert (Then)
        assertEquals(expected, actual.replaceAll("\r\n", "\n"));
    }

    @Test
    @Disabled
    public void testGenerateGoldenMaster() {
        goldenMaster.generateGoldenMaster();
    }

    @AfterEach
    public void end() {
        goldenMaster = null;
    }

}
