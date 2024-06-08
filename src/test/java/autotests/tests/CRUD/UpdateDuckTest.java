package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class UpdateDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что у уточки можно изменить цвет и высоту")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        String material = "rubber";
        String sound = "quack";

        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material(material)
                .sound(sound)
                .wingsState("FIXED");

        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        updateDuck(runner, "${duckId}", "blue", 1, material, sound);
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + "${duckId}" + " is updated\"}");
        // + проверка в БД, что изменили все, что требовалось

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // + проверить что ее нет в бд

    }

    @Test(description = "Проверка того, что у уточки можно изменить цвет и звук")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";

        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material(material)
                .sound(sound)
                .wingsState("FIXED");

        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        updateDuck(runner, "${duckId}", "blue", height, material, "moooo");
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + "${duckId}" + " is updated\"}");
        // + проверка в БД, что изменили все, что требовалось

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // + проверить что ее нет в бд

    }
}
