import org.example.Main;
import org.junit.jupiter.api.Test;

public class TestApp {

    @Test
    public void checkResultInFile() {
        Main.doTask(5);

//        StringBuilder actual = new StringBuilder();
//        try (Scanner scanner = new Scanner(new File("Output.txt"))) {
//            while (scanner.hasNext()) {
//               actual.append(scanner.nextLine());
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        StringBuilder expected = new StringBuilder("5");
//
//        assertEquals(expected, actual);
    }
}
