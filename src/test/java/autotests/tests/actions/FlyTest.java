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
@Feature("Эндпоинт /api/duck/action/fly")
public class FlyTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка с активными крыльями летает (wingState = ACTIVE)")
    @CitrusTest
    public void successfulFlyWingsActive(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        flyDuck(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithActiveWings.json");
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не летает (wingState = FIXED)")
    @CitrusTest
    public void successfulFlyWingsFixed(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");
        flyDuck(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithFixedWings.json");
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состояниине летает (wingState = UNDEFINED)")
    @CitrusTest
    public void successfulFlyWingsNull(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");
        flyDuck(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithUndefinedWings.json");
    }
}
