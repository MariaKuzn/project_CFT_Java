package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;
    @Autowired
    protected SingleConnectionDataSource db;
    public String returnInsertDuckSQLFromProperties(
            int id, String color, double height, String material, String sound, String wingsState) {
        return new StringBuilder()
                .append("insert into DUCK (id, color, height, material, sound, wings_state) values (")
                .append(id)
                .append(", '")
                .append(color)
                .append("', ")
                .append(height)
                .append(", '")
                .append(material)
                .append("', '")
                .append(sound)
                .append("', '")
                .append(wingsState)
                .append("');")
                .toString();
    }

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(db)
                .statement(sql));
    }

    public void createDuckFromObject(TestCaseRunner runner, Object object) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(object, new ObjectMapper())));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
        //Проверка ответа что удалилось
    }

    // валидация для всех эндпойнтов кроме create после создания утки
    public void validateStatusAndSaveId(TestCaseRunner runner, HttpStatus status) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    //валидация ответа по jsonPath
    public void validateResponseStatusAndJSONPath(TestCaseRunner runner, HttpStatus status,
                                                  JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath));
    }

    //валидация ответа по ресурсу
    public void validateResponseStatusAndBodyByResource(TestCaseRunner runner, HttpStatus status,
                                                        String expectedPayload) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload)));
    }

    //валидация ответа по обьекту
    public void validateResponseStatusAndBodyByObject(TestCaseRunner runner, HttpStatus status, Object body) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    //валидация ответа по String
    public void validateResponseStatusAndBodyAsString(TestCaseRunner runner, HttpStatus status,
                                                      String responseMessage) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage));
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
    public boolean isEven(int number) {
        int n;
        try {
            n = number;
            return n % 2 == 0;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
