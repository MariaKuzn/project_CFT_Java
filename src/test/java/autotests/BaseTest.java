package autotests;

import autotests.payloads.Duck;
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
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;
    @Autowired
    protected SingleConnectionDataSource db;

    @Step("Подготовка/очистка базы данных")
    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(db)
                .statement(sql));
    }
    public void checkDuckPropertiesInDB(TestCaseRunner runner, Duck duck){
        runner.$(query(db)
                .statement("SELECT * FROM DUCK WHERE ID=" + duck.id())
                .validate("COLOR", duck.color())
                .validate("HEIGHT", String.valueOf(duck.height()))
                .validate("MATERIAL", duck.material())
                .validate("SOUND", duck.sound())
                .validate("WINGS_STATE", duck.wingsState()));
    }

    @Step("Send get request")
    protected void sendGetRequest(TestCaseRunner runner, String path, String queName, String queValue) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get(path)
                .queryParam(queName, queValue));
    }
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
}
