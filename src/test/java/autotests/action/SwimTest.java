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

public class SwimTest extends CommonMethod {
    @Test(description = "Проверка того, что уточка плавает. Существующий id")
    @CitrusTest
    public void successfulSwimExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        swimDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "I’m swimming"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка не может плавть, т.к. не найдена. Неуществующий id")
    @CitrusTest
    public void failedSwimNotExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);
        deleteDuck(runner, "${duckId}");

        swimDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.NOT_FOUND,
                jsonPath().expression("$.error", "Duck not found")
                        .expression("$.message", "Duck with id = " + "${duckId}" + " is not found"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    private void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
}
