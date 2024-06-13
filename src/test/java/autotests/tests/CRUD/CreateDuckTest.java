package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class CreateDuckTest extends DuckCRUDClient {

    @Test(description = "Проверка того, что уточка создана. Материал = rubber")
    @CitrusTest
    public void successfulCreateRubber(@Optional @CitrusResource TestCaseRunner runner,
                                       @Optional @CitrusResource TestContext context) {
        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(ctx ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        createDuckFromObject(runner, duck);

        validateStatusBodyAndSaveIdByJsonPath(runner, HttpStatus.OK, jsonPath()
                .expression("$.color", duck.color())
                .expression("$.height", duck.height())
                .expression("$.material", duck.material())
                .expression("$.sound", duck.sound())
                .expression("$.wingsState", duck.wingsState()));
        duck.id(Integer.parseInt(context.getVariables().get("duckId").toString()));

        // + Проверить что утка есть в БД, все параметры совпадают
        checkDuckPropertiesInDB(runner, duck);
    }

    @Test(description = "Проверка того, что уточка создана. Материал = wood")
    @CitrusTest
    public void successfulCreateWood(@Optional @CitrusResource TestCaseRunner runner,
                                     @Optional @CitrusResource TestContext context) {
        Duck duck = new Duck()
                .color("green")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(doFinally().actions(ctx ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        createDuckFromObject(runner, duck);
        validateStatusBodyAndSaveIdByJsonPath(runner, HttpStatus.OK, jsonPath()
                .expression("$.color", duck.color())
                .expression("$.height", duck.height())
                .expression("$.material", duck.material())
                .expression("$.sound", duck.sound())
                .expression("$.wingsState", duck.wingsState()));
        duck.id(Integer.parseInt(context.getVariables().get("duckId").toString()));

        // + Проверить что утка есть в БД, все параметры совпадают
        checkDuckPropertiesInDB(runner, duck);
    }

    @Test(dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload", "runner", "context"})
    public void successfulDuckCreate(Duck payload, @Optional @CitrusResource TestCaseRunner runner,
                                     @Optional @CitrusResource TestContext context) {
        runner.$(doFinally().actions(ctx ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        createDuckFromObject(runner, payload);
        validateStatusBodyAndSaveIdByJsonPath(runner, HttpStatus.OK, jsonPath()
                .expression("$.color", payload.color())
                .expression("$.height", payload.height())
                .expression("$.material", payload.material())
                .expression("$.sound", payload.sound())
                .expression("$.wingsState", payload.wingsState()));
        payload.id(Integer.parseInt(context.getVariables().get("duckId").toString()));

        // + Проверить что утка есть в БД, все параметры совпадают
        checkDuckPropertiesInDB(runner, payload);
    }

    @DataProvider(name = "duckList")
    public Object[][] DuckProvider() {
        return new Object[][]{
                {new Duck().color("green").height(0.15).material("rubber").sound("quack").wingsState("ACTIVE"), null, null},
                {new Duck().color("green").height(1).material("rubber").sound("quack").wingsState("ACTIVE"), null, null},
                {new Duck().color("green").height(0.15).material("glass").sound("quack").wingsState("ACTIVE"), null, null},
                {new Duck().color("green").height(0.15).material("rubber").sound("ku").wingsState("ACTIVE"), null, null},
                {new Duck().color("green").height(0.15).material("rubber").sound("quack").wingsState("FIXED"), null, null}
        };
    }
}
