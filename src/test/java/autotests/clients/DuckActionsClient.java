package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;
    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}"));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
        //Проверка ответа что удалилось
    }

    public void validateStatusAndSaveId(TestCaseRunner runner, HttpStatus status) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
    public void validateResponseStatusAndJSONPath(TestCaseRunner runner, HttpStatus status,
                                                  JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath));
    }
    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void getProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    public String generateQuackMessage(String sound, int repetitionCount, int soundCount) {
        if (sound.isEmpty() || repetitionCount < 1 || soundCount < 1) {
            return null;
        }

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < repetitionCount; i++) {
            for (int j = 0; j < soundCount; j++) {
                message.append(sound);
                message.append((j < soundCount - 1) ? "-" : "");
            }
            message.append((i < repetitionCount - 1) ? ", " : "");
        }
        return message.toString();
    }

    // Проверка числа на четность, number - передается в виде строки
    public boolean isEven(String number) {
        int n;
        try {
            n = Integer.parseInt(number);
            return n % 2 == 0;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
