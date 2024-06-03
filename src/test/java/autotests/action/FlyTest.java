package autotests.action;

import autotests.CommonMethod;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class FlyTest extends CommonMethod {

    @Test(description = "Проверка того, что уточка с активными крыльями летает (wingState = ACTIVE)")
    @CitrusTest
    public void successfulFlyWingsActive(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "I am flying :)"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не летает (wingState = FIXED)")
    @CitrusTest
    public void successfulFlyWingsFixed(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "FIXED");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "I can not fly :C"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состояниине летает (wingState = UNDEFINED)")
    @CitrusTest
    public void successfulFlyWingsNull(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "UNDEFINED");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Wings are not detected :("));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    private void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }
}
