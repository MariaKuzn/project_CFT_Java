package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
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
@Feature("Эндпоинт /api/duck/delete")
public class DeleteDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что уточка удаляется")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
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

        deleteDuck(runner, String.valueOf(duck.id()));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, new Message().message("Duck is deleted"));

        checkDuckIsAbsent(runner, duck.id());
    }
}
