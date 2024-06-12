package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
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
        Duck duck = new Duck()
                .id((int) (Math.round(Math.random() * 1000) * 2 + 1))
                .color("yellow")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        int repetitionCount = 2;
        int soundCount = 3;

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        quackDuck(runner, String.valueOf(duck.id()), String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"sound\": \"" + generateQuackMessage(duck.sound(), repetitionCount, soundCount) + "\"}");
    }

    @Test(description = "Проверка того, что уточка крякает. Четный id, sound = quack")
    @CitrusTest
    public void successfulQuackEvenIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        //id - четный
        Duck duck = new Duck()
                .id((int) (Math.round(Math.random() * 1000) * 2))
                .color("yellow")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        int repetitionCount = 2;
        int soundCount = 3;

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        quackDuck(runner, String.valueOf(duck.id()), String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"sound\": \"" + generateQuackMessage(duck.sound(), repetitionCount, soundCount) + "\"}");
    }
}
