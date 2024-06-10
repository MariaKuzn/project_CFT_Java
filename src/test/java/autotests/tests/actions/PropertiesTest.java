package autotests.tests.actions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
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
@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/properties")
public class PropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка получения Properties для уточки. Четный id, material = wood")
    @CitrusTest
    public void getPropertiesEvenIdWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        //id - четный
        int id = (int) (Math.round(Math.random() * 1000) * 2);
        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);

        getProperties(runner, String.valueOf(id));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому
        runner.$(query(db)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", String.valueOf(height))
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    @Test(description = "Проверка получения Properties для уточки. Нечетный id, material = rubber")
    @CitrusTest
    public void getPropertiesOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        //id - нечетный
        int id = (int) (Math.round(Math.random() * 1000) * 2 + 1);
        String color = "yellow";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        DuckProperties duckProperties =
                new DuckProperties()
                        .color(color)
                        .height(height)
                        .material(material)
                        .sound(sound)
                        .wingsState(wingsState);

        getProperties(runner, String.valueOf(id));
        validateResponseStatusAndBodyByObject(runner, HttpStatus.OK, duckProperties);

        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому
        runner.$(query(db)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", String.valueOf(height))
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }
}
