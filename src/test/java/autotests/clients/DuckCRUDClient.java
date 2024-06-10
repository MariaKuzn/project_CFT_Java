package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckCRUDClient extends TestNGCitrusSpringSupport {
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

    @Step("Эндпоинт для создания уточки")
    public void createDuckFromObject(TestCaseRunner runner, Object object) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(object, new ObjectMapper())));
    }

    @Step("Эндпоинт для удаления уточки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    @Step("Эндпоинт для обновления уточки")
    public void updateDuck(TestCaseRunner runner, String id, String color, double height, String material,
                           String sound) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound));
    }

    // валидация для create
    @Step("Валидация ответа для созданой уточки и сохранение ее id")
    public void validateStatusBodyAndSaveIdByJsonPath(TestCaseRunner runner, HttpStatus status,
                                                      JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath)
                .extract(fromBody().expression("$.id", "duckId")));
    }

    //валидация ответа по String
    @Step("Валидация ответа на запрос по строке")
    public void validateResponseStatusAndBodyAsString(TestCaseRunner runner, HttpStatus status, String responseMessage) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage));
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
}
