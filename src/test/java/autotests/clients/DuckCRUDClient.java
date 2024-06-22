package autotests.clients;

import autotests.BaseTest;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCRUDClient extends BaseTest {
    @Autowired
    protected HttpClient yellowDuckService;
    @Autowired
    protected SingleConnectionDataSource db;

    @Step("Эндпоинт для создания уточки")
    protected void createDuckFromObject(TestCaseRunner runner, Object object) {
        sendPostRequest(runner, "/api/duck/create", object);
    }

    @Step("Эндпоинт для удаления уточки")
    protected void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, "/api/duck/delete", "id", id);
    }

    @Step("Эндпоинт для обновления уточки")
    protected void updateDuck(TestCaseRunner runner, Duck duck) {
        sendPutRequest(runner, "/api/duck/update",
                "?id=" + duck.id()
                        + "&color=" + duck.color()
                        + "&height=" + duck.height()
                        + "&material=" + duck.material()
                        + "&sound=" + duck.sound());
    }

    // валидация только для create
    @Step("Валидация ответа для созданой уточки и сохранение ее id")
    protected void validateStatusBodyAndSaveIdByJsonPath(TestCaseRunner runner, HttpStatus status,
                                                      JsonPathMessageValidationContext.Builder jsonPath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath)
                .extract(fromBody().expression("$.id", "duckId")));
    }
    @Step("Проверка, что утка отсутствует в БД")
    protected void checkDuckIsAbsent(TestCaseRunner runner, int id){
        checkSomethingInDB(runner, "SELECT COUNT(ID) AS AMOUNT FROM DUCK WHERE ID=" + id, "AMOUNT", "0");
    }
}
