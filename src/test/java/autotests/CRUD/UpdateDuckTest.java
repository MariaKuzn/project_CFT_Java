package autotests.CRUD;

import autotests.CommonMethod;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class UpdateDuckTest extends CommonMethod {
    @Test(description = "Проверка того, что у уточки можно изменить цвет и высоту")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        String newColor = "blue";
        double newHeight = 1;

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        updateDuck(runner, "${duckId}", newColor, newHeight, material, sound);
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Duck with id = " + "${duckId}" + " is updated"));
        // + проверка в БД, что изменили все, что требовалось

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // + проверить что ее нет в бд

    }

    @Test(description = "Проверка того, что у уточки можно изменить цвет и звук")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        String newColor = "blue";
        String newSound = "moooo";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        updateDuck(runner, "${duckId}", newColor, height, material, newSound);
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Duck with id = " + "${duckId}" + " is updated"));
        // + проверка в БД, что изменили все, что требовалось

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // + проверить что ее нет в бд

    }

    // обязательные параметры
    private void updateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound));
    }

    // все параметры
    private void updateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }
}
