package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
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

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/update")
public class UpdateDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что у уточки можно изменить цвет и высоту")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        Duck newDuck = new Duck()
                .id(duck.id())
                .color("blue")
                .height(1)
                .material(duck.material())
                .sound(duck.sound())
                .wingsState(duck.wingsState());

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        updateDuck(runner, newDuck);
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + duck.id() + " is updated\"}");

        // + проверка в БД, что изменили все, что требовалось и не изменилось, что не требовалось
        checkDuckPropertiesInDB(runner, newDuck);
    }

    @Test(description = "Проверка того, что у уточки можно изменить цвет и звук")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        Duck newDuck = new Duck()
                .id(duck.id())
                .color("blue")
                .height(duck.height())
                .material(duck.material())
                .sound("moooo")
                .wingsState(duck.wingsState());

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        updateDuck(runner, newDuck);
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + duck.id() + " is updated\"}");

        // + проверка в БД, что изменили все, что требовалось и не изменилось, что не требовалось
        checkDuckPropertiesInDB(runner, newDuck);
    }
}
