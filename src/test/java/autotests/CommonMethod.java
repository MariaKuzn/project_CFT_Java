package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CommonMethod extends TestNGCitrusSpringSupport {
    //Все параметры
    public void createDuck(TestCaseRunner runner,
                           String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
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
        runner.$(http().client("http://localhost:2222")
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
        //Проверка ответа что удалилось
    }

    public void validateStatusAndSaveId(TestCaseRunner runner, HttpStatus status) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(status)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
    public void validateStatusBodyAndSaveId(TestCaseRunner runner, HttpStatus status, JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath)
                .extract(fromBody().expression("$.id", "duckId")));
    }

    public void validateResponseStatusAndJSONPath(TestCaseRunner runner, HttpStatus status,
                                                  JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath));
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
