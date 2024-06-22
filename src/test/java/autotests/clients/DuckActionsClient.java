package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckActionsClient extends BaseTest {

    @Step("Эндпоинт для полета уточки")
    protected void flyDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/fly", "?id=" + id);
    }

    @Step("Эндпоинт для получения свойств уточки")
    protected void getProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/properties", "?id=" + id);
    }

    @Step("Эндпоинт для плавания уточки")
    protected void swimDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/swim", "?id=" + id);
    }

    @Step("Эндпоинт для крякания уточки")
    protected void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        sendGetRequest(runner, "/api/duck/action/quack",
                "?id=" + id + "&repetitionCount=" + repetitionCount + "&soundCount=" + soundCount);
    }

    protected String generateQuackMessage(String sound, int repetitionCount, int soundCount) {
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
}
