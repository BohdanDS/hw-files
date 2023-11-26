package files;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.QuizModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonReaderTest {

    private final ClassLoader classLoader = JsonReaderTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Проверка данных JSON")
    void checkOptionsForFirstQuestion() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("sample.json")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                QuizModel data = objectMapper.readValue(isr, QuizModel.class);

                Assertions.assertEquals("0001", data.id);
                Assertions.assertEquals("Cake", data.name);
                Assertions.assertEquals(List.of(
                        "Regular",
                        "Chocolate",
                        "Blueberry",
                        "Devil's Food"), data.batters);

            }
        }
    }

}
