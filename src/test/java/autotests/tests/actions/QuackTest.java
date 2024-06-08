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

public class QuackTest extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка крякает. Нечетный id, sound = quack")
    @CitrusTest
    public void successfulQuackOddIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        String sound = "quack";
        int repetitionCount = 2;
        int soundCount = 3;

        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound(sound)
                .wingsState("ACTIVE");
        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //Проверка на четность. В случае создания новых id по порядку (как у нас) - этого достаточно.
        if (isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuckFromObject(runner, duck);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        quackDuck(runner, "${duckId}", String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK, "{\"sound\": \"" + generateQuackMessage(sound, repetitionCount, soundCount) + "\"}");

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка крякает. Четный id, sound = quack")
    @CitrusTest
    public void successfulQuackEvenIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        String sound = "quack";
        int repetitionCount = 2;
        int soundCount = 3;

        Duck duck = new Duck()
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound(sound)
                .wingsState("ACTIVE");
        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //Проверка на нечетность. В случае создания новых id по порядку (как у нас) - этого достаточно.
        if (!isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuckFromObject(runner, duck);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        quackDuck(runner, "${duckId}", String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK, "{\"sound\": \"" + generateQuackMessage(sound, repetitionCount, soundCount) + "\"}");

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }
}
