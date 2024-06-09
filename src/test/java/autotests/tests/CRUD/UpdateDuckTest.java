package autotests.tests.CRUD;

import autotests.clients.DuckCRUDClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class UpdateDuckTest extends DuckCRUDClient {
    @Test(description = "Проверка того, что у уточки можно изменить цвет и высоту")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {

        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        String newColor = "blue";
        double newHeight = 1;

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));

        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        updateDuck(runner, String.valueOf(id), newColor, newHeight, material, sound);
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + id + " is updated\"}");

        // + проверка в БД, что изменили все, что требовалось и не изменилось, что не требовалось
        runner.$(query(db)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", newColor)
                .validate("HEIGHT", String.valueOf(newHeight))
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    @Test(description = "Проверка того, что у уточки можно изменить цвет и звук")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        String newColor = "blue";
        String newSound = "moooo";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));

        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        updateDuck(runner, String.valueOf(id), newColor, height, material, newSound);
        validateResponseStatusAndBodyAsString(runner, HttpStatus.OK,
                "{\"message\": \"Duck with id = " + id + " is updated\"}");

        // + проверка в БД, что изменили все, что требовалось и не изменилось, что не требовалось
        runner.$(query(db)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", newColor)
                .validate("HEIGHT", String.valueOf(height))
                .validate("MATERIAL", material)
                .validate("SOUND", newSound)
                .validate("WINGS_STATE", String.valueOf(wingsState)));
    }
}
