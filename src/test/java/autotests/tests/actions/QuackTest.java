package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/quack")
public class QuackTest extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка крякает. Нечетный id, sound = quack")
    @CitrusTest
    public void successfulQuackOddIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        //id - нечетный
        int id = (int) (Math.round(Math.random() * 1000) * 2 + 1);
        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        int repetitionCount = 2;
        int soundCount = 3;

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        quackDuck(runner, String.valueOf(id), String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"sound\": \"" + generateQuackMessage(sound, repetitionCount, soundCount) + "\"}");
    }

    @Test(description = "Проверка того, что уточка крякает. Четный id, sound = quack")
    @CitrusTest
    public void successfulQuackEvenIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        //id - четный
        int id = (int) (Math.round(Math.random() * 1000) * 2);
        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        int repetitionCount = 2;
        int soundCount = 3;

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        quackDuck(runner, String.valueOf(id), String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"sound\": \"" + generateQuackMessage(sound, repetitionCount, soundCount) + "\"}");
    }
}
