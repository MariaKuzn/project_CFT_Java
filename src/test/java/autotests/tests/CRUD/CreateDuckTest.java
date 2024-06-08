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

public class CreateDuckTest extends DuckCRUDClient {

    @Test(description = "Проверка того, что уточка создана. Материал = rubber")
    @CitrusTest
    public void successfulCreateRubber(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        Duck duck =
                new Duck()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);

        createDuckFromObject(runner, duck);
        validateStatusBodyAndSaveIdByJsonPath(runner, HttpStatus.OK, jsonPath()
                .expression("$.color", color)
                .expression("$.height", height)
                .expression("$.material", material)
                .expression("$.sound", sound)
                .expression("$.wingsState", wingsState));

        deleteDuck(runner, "${duckId}");
        // + Проверить что утка есть в БД, все параметры совпадают

    }

    @Test(description = "Проверка того, что уточка создана. Материал = wood")
    @CitrusTest
    public void successfulCreateWood(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        Duck duck =
                new Duck()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);
        createDuckFromObject(runner, duck);

        validateStatusBodyAndSaveIdByJsonPath(runner, HttpStatus.OK, jsonPath()
                .expression("$.color", color)
                .expression("$.height", height)
                .expression("$.material", material)
                .expression("$.sound", sound)
                .expression("$.wingsState", wingsState));

        deleteDuck(runner, "${duckId}");
        // + Проверить что утка есть в БД, все параметры совпадают
    }
}
