package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class BaseTest extends TestNGCitrusSpringSupport {


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
