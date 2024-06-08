package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что уточка удаляется")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {

        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuckFromObject(runner, duck);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, new Message().message("Duck is deleted"));
        // + проверить что ее нет в бд
    }
}
