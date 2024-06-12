package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/swim")
public class SwimTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка плавает. Существующий id")
    @CitrusTest
    public void successfulSwimExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .id((int) Math.round(Math.random() * 1000))
                .color("yellow")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + duck.id())));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state) "
                + "values (" + duck.id() + ", '" + duck.color() + "', " + duck.height() + ", '" + duck.material()
                + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");

        swimDuck(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, new Message().message("I'm swimming"));
    }

    @Test(description = "Проверка того, что уточка не может плавть, т.к. не найдена. Неуществующий id")
    @CitrusTest
    public void failedSwimNotExistingId(@Optional @CitrusResource TestCaseRunner runner,
                                        @Optional @CitrusResource TestContext context) {
        int id = 0;
        boolean duckExist = true;

        // Проверить что в бд нет утки с таким id запросом, если есть - сгенерировать новый id
        while (duckExist) {
            id = (int) Math.round(Math.random() * 1000);
            runner.$(query(db)
                    .statement("SELECT COUNT(ID) AS AMOUNT FROM DUCK WHERE ID=" + id)
                    .extract("AMOUNT", "amount"));
            duckExist = !"0".equals(context.getVariables().get("amount"));
        }

        swimDuck(runner, String.valueOf(id));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.NOT_FOUND,
                new Message().message("Duck with id = " + id + " is not found"));
    }
}
