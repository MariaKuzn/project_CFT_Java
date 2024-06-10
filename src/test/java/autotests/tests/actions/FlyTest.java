package autotests.tests.actions;


import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;


public class FlyTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка с активными крыльями летает (wingState = ACTIVE)")
    @CitrusTest
    public void successfulFlyWingsActive(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithActiveWings.json");

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не летает (wingState = FIXED)")
    @CitrusTest
    public void successfulFlyWingsFixed(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithFixedWings.json");


        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состояниине летает (wingState = UNDEFINED)")
    @CitrusTest
    public void successfulFlyWingsNull(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");
        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithUndefinedWings.json");

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }
}
