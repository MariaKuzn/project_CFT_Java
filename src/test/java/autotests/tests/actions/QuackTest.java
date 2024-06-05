package autotests.tests.actions;

import autotests.CommonMethod;

public class QuackTest extends CommonMethod {
    /*
    @Test(description = "Проверка того, что уточка крякает. Нечетный id, sound = quack")
    @CitrusTest
    public void successfulQuackOddIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        String sound = "quack";
        int repetitionCount = 2;
        int soundCount = 3;

        createDuck(runner, "yellow", 0.15, "rubber", sound, "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //Проверка на четность. В случае создания новых id по порядку (как у нас) - этого достаточно.
        if (isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, "yellow", 0.15, "rubber", sound, "ACTIVE");
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        quackDuck(runner, "${duckId}", String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.sound", generateQuackMessage(sound, repetitionCount, soundCount)));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    @Test(description = "Проверка того, что уточка крякает. Четный id, sound = quack")
    @CitrusTest
    public void successfulQuackEvenIdQuackSound(@Optional @CitrusResource TestCaseRunner runner) {
        String sound = "quack";
        int repetitionCount = 2;
        int soundCount = 3;

        createDuck(runner, "yellow", 0.15, "rubber", sound, "ACTIVE");
        validateStatusAndSaveId(runner, HttpStatus.OK);

        //Проверка на нечетность. В случае создания новых id по порядку (как у нас) - этого достаточно.
        if (!isEven("${duckId}")) {
            deleteDuck(runner, "${duckId}");
            createDuck(runner, "yellow", 0.15, "rubber", sound, "ACTIVE");
            validateStatusAndSaveId(runner, HttpStatus.OK);
        }

        quackDuck(runner, "${duckId}", String.valueOf(repetitionCount), String.valueOf(soundCount));
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK,
                jsonPath().expression("$.sound", generateQuackMessage(sound, repetitionCount, soundCount)));

        deleteDuck(runner, "${duckId}");
        validateResponseStatusAndJSONPath(runner, HttpStatus.OK, jsonPath().expression("$.message", "Duck is deleted"));
        // проверить что ее нет в бд
    }

    private void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    public String generateQuackMessage(String sound, int repetitionCount, int soundCount) {
        if (sound.isEmpty() || repetitionCount < 1 || soundCount < 1) {
            return null;
        }

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < repetitionCount; i++) {
            for (int j = 0; j < soundCount; j++) {
                message.append(sound);
                message.append((j < soundCount - 1) ? "-" : "");
            }
            message.append((i < repetitionCount - 1) ? ", " : "");
        }
        return message.toString();
    }

     */
}
