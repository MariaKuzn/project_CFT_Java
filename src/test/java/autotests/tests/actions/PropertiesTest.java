package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class PropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка получения Properties для уточки. Четный id, material = wood")
    @CitrusTest
    public void getPropertiesEvenIdWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (!isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, color, height, material, sound, wingsState);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }
        getProperties(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.color", color)
                        .expression("$.height", height)
                        .expression("$.material", material)
                        .expression("$.sound", sound)
                        .expression("$.wingsState", wingsState));
        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому

        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка получения Properties для уточки. Нечетный id, material = rubber")
    @CitrusTest
    public void getPropertiesOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, color, height, material, sound, wingsState);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }
        getProperties(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.color", color)
                        .expression("$.height", height)
                        .expression("$.material", material)
                        .expression("$.sound", sound)
                        .expression("$.wingsState", wingsState));
        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому

        deleteDuck(runner, "${duckId}");
    }

}
