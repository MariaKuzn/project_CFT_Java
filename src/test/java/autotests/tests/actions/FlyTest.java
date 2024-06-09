package autotests.tests.actions;


import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


public class FlyTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка с активными крыльями летает (wingState = ACTIVE)")
    @CitrusTest
    public void successfulFlyWingsActive(@Optional @CitrusResource TestCaseRunner runner) {

        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "ACTIVE";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        flyDuck(runner, String.valueOf(id));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithActiveWings.json");
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не летает (wingState = FIXED)")
    @CitrusTest
    public void successfulFlyWingsFixed(@Optional @CitrusResource TestCaseRunner runner) {
        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        flyDuck(runner, String.valueOf(id));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithFixedWings.json");
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состояниине летает (wingState = UNDEFINED)")
    @CitrusTest
    public void successfulFlyWingsNull(@Optional @CitrusResource TestCaseRunner runner) {
        int id = (int) Math.round(Math.random() * 1000);
        String color = "green";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "UNDEFINED";

        runner.$(doFinally().actions(context -> databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=" + id)));
        databaseUpdate(runner, returnInsertDuckSQLFromProperties(id, color, height, material, sound, wingsState));

        flyDuck(runner, String.valueOf(id));
        validateResponseStatusAndBodyByResource(runner, HttpStatus.OK,
                "flyDuckTest/flyDuckWithUndefinedWings.json");
    }
}
