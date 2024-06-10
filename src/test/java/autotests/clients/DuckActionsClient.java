package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;
    @Autowired
    protected SingleConnectionDataSource db;

    @Step("Подготовка/очистка базы данных")
    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(db)
                .statement(sql));
    }

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

    //валидация ответа по ресурсу
    @Step("Валидация ответа на запрос по ресурсу")
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
    @Step("Валидация ответа на запрос по объекту")
    public void validateResponseStatusAndBodyByObject(TestCaseRunner runner, HttpStatus status, Object body) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    //валидация ответа по String
    @Step("Валидация ответа на запрос по строке")
    public void validateResponseStatusAndBodyAsString(TestCaseRunner runner, HttpStatus status,
                                                      String responseMessage) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage));
    }

    @Step("Эндпоинт для полета уточки")
    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    @Step("Эндпоинт для получения свойств уточки")
    public void getProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    @Step("Эндпоинт для плавания уточки")
    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    @Step("Эндпоинт для крякания уточки")
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
}
