package autotests.tests.actions;


public class FlyTest  {
    /*

    @Test(description = "Проверка того, что уточка с активными крыльями летает (wingState = ACTIVE)")
    @CitrusTest
    public void successfulFlyWingsActive(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "I’m flying"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не летает (wingState = FIXED)")
    @CitrusTest
    public void successfulFlyWingsFixed(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "FIXED");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "I can’t fly"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состояниине летает (wingState = UNDEFINED)")
    @CitrusTest
    public void successfulFlyWingsNull(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "green", 0.15, "rubber", "quack", "UNDEFINED");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        flyDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.message", "Wings are not detected"));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    private void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

     */
}
