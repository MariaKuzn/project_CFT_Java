package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class DeleteDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что уточка удаляется")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {

        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // + проверить что ее нет в бд
    }
}
