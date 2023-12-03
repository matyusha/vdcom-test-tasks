import org.example.CountFileWriter;
import org.example.RewriteFileTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApp {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final Path path = Paths.get("Output-test.txt");

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        try {
            Files.writeString(path, "0", StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkResult() {
        RewriteFileTask task = new RewriteFileTask(new Semaphore(1),
                new CountFileWriter(path,5));

        task.run();

        String actualFileData;
        try {
            actualFileData = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("5", actualFileData);

        String actual = outputStreamCaptor.toString();
        String expected = """
                old value: 0 new value: 1 thread: main\r
                old value: 1 new value: 2 thread: main\r
                old value: 2 new value: 3 thread: main\r
                old value: 3 new value: 4 thread: main\r
                old value: 4 new value: 5 thread: main\r
                """;
        assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
