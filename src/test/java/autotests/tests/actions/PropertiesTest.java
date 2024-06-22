package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.DuckProperties;
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
@Feature("Эндпоинт /api/duck/action/properties")
public class PropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка получения Properties для уточки. Четный id, material = wood")
    @CitrusTest
    public void getPropertiesEvenIdWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        //id - четный
        Duck duck = new Duck()
                .id((int) (Math.round(Math.random() * 1000) * 2))
                .color("yellow")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(duck.color())
                        .height(duck.height())
                        .material(duck.material())
                        .sound(duck.sound())
                        .wingsState(duck.wingsState());

        getProperties(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому
        checkDuckPropertiesInDB(runner, duck);
    }

    @Test(description = "Проверка получения Properties для уточки. Нечетный id, material = rubber")
    @CitrusTest
    public void getPropertiesOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        //id - нечетный
        Duck duck = new Duck()
                .id((int) (Math.round(Math.random() * 1000) * 2 + 1))
                .color("yellow")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(duck.color())
                        .height(duck.height())
                        .material(duck.material())
                        .sound(duck.sound())
                        .wingsState(duck.wingsState());

        getProperties(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому
        checkDuckPropertiesInDB(runner, duck);
    }
}
