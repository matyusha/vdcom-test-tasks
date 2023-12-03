import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApp {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void firstDecisionTest() {
        Main.firstDecision(15);
        String actual = outputStreamCaptor.toString();
        String expected = "1\r\n2\r\nFoo\r\n4\r\nBar\r\nFoo\r\n7\r\n8\r\nFoo\r\nBar\r\n11\r\nFoo\r\n13\r\n14\r\nFooBar\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void secondDecisionTest() {
        Main.secondDecision(15);
        String actual = outputStreamCaptor.toString();
        String expected = "1\r\n2\r\nFoo\r\n4\r\nBar\r\nFoo\r\n7\r\n8\r\nFoo\r\nBar\r\n11\r\nFoo\r\n13\r\n14\r\nFooBar\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void thirdDecisionTest() {
        Main.thirdDecision(15);
        String actual = outputStreamCaptor.toString();
        String expected = "1\r\n2\r\nFoo\r\n4\r\nBar\r\nFoo\r\n7\r\n8\r\nFoo\r\nBar\r\n11\r\nFoo\r\n13\r\n14\r\nFooBar\r\n";
        assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
