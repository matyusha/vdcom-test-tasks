import org.example.data.MetricValue;
import org.example.data.Proportion;
import org.example.data.Task;
import org.example.exceptions.ConversionNotPossibleException;
import org.example.exceptions.MetricsNotFoundException;
import org.example.exceptions.ProportionFormatException;
import org.example.services.DataService;
import org.example.services.MetricsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestApp {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final DataService dataService = new DataService();
    private final MetricsService metricsService = new MetricsService(dataService);


    @Test
    public void checkProportionConstructor() throws ProportionFormatException {
        Proportion actual = new Proportion("1024 byte = 1 kilobyte");
        Proportion expected = new Proportion(new MetricValue(1024, "byte"), new MetricValue(1, "kilobyte"));

        assertEquals(expected, actual);
    }

    @Test
    public void throwExceptionProportionConstructor() {
        assertThrows(ProportionFormatException.class, () -> new Proportion("1 m sm"));
    }

    @Test
    public void checkTaskConstructor() throws ProportionFormatException {
        Task actual = new Task("1 m = ? sm");
        Task expected = new Task(new MetricValue(1, "m"), "sm");

        assertEquals(expected, actual);
    }

    @Test
    public void throwExceptionTaskConstructor() {
        assertThrows(ProportionFormatException.class, () -> new Proportion("1 m sm"));
    }

    @BeforeEach
    public void setUp() throws ProportionFormatException {
        System.setOut(new PrintStream(outputStreamCaptor));

        dataService.addProportion(new Proportion("1024 byte = 1 kilobyte"));
        dataService.addProportion(new Proportion("2 bar = 12 ring"));
        dataService.addProportion(new Proportion("16.8 ring = 2 pyramid"));
        dataService.addProportion(new Proportion("4 hare = 1 cat"));
        dataService.addProportion(new Proportion("5 cat = 0.5 giraffe"));
        dataService.addProportion(new Proportion("1 byte = 8 bit"));
        dataService.addProportion(new Proportion("15 ring = 2.5 bar"));
        dataService.addProportion(new Proportion("1024 kilobyte = 1 megabyte"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-tasks.csv", numLinesToSkip = 1)
    void testTasksResults(String proportion, String task)
            throws ProportionFormatException, ConversionNotPossibleException, MetricsNotFoundException {
        assertEquals(proportion, metricsService.doTask(new Task(task)).toString());
    }

    @Test
    public void throwExceptionDoTask() {
        assertThrows(ConversionNotPossibleException.class, () -> metricsService.doTask(new Task("0.5 byte = ? cat")));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
