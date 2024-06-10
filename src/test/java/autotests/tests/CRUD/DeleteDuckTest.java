package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/delete")
public class DeleteDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что уточка удаляется")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {

        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        //на всякий случай. вдруг тест упадет до удаления утки или утка не удалится через api
        try {
            runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        } catch (Exception ignored) {
        }

        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        deleteDuck(runner, String.valueOf(id));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, new Message().message("Duck is deleted"));

        runner.$(query(db)
                .statement("SELECT COUNT(ID) AS AMOUNT FROM DUCK WHERE ID=" + id)
                .validate("AMOUNT", "0"));
    }
}
