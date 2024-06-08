package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка получения Properties для уточки. Четный id, material = wood")
    @CitrusTest
    public void getPropertiesEvenIdWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);

        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (!isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuckFromObject(runner, duck);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        getProperties(runner, "${duckId}");
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

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

        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);

        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuckFromObject(runner, duck);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        getProperties(runner, "${duckId}");
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому

        deleteDuck(runner, "${duckId}");
    }
}
