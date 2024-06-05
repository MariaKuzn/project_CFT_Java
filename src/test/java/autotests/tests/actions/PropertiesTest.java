package autotests.tests.actions;

public class PropertiesTest {
    /*
    @Test(description = "Проверка получения Properties для уточки. Четный id, material = wood")
    @CitrusTest
    public void getPropertiesEvenIdWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String color = "yellow";
        double height = 0.15;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (!isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, color, height, material, sound, wingsState);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }
        getProperties(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.color", color)
                        .expression("$.height", height)
                        .expression("$.material", material)
                        .expression("$.sound", sound)
                        .expression("$.wingsState", wingsState));
        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому

        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка получения Properties для уточки. Нечетный id, material = rubber")
    @CitrusTest
    public void getPropertiesOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.15;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //в случае создания новых id по порядку (как у нас) - этого достаточно.
        if (isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, color, height, material, sound, wingsState);
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }
        getProperties(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.color", color)
                        .expression("$.height", height)
                        .expression("$.material", material)
                        .expression("$.sound", sound)
                        .expression("$.wingsState", wingsState));
        // + Проверить что полученные свойства совпадают с БД на всякий случай - вдруг хранит по-другому

        deleteDuck(runner, "${duckId}");
    }

    public void getProperties(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

         */
}
