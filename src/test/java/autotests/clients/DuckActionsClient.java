package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends BaseTest {

    @Step("Эндпоинт для полета уточки")
    public void flyDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/fly", "id", id);
    }

    @Step("Эндпоинт для получения свойств уточки")
    public void getProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/properties", "id", id);
    }

    @Step("Эндпоинт для плавания уточки")
    public void swimDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, "/api/duck/action/swim", "id", id);
    }

    @Step("Эндпоинт для крякания уточки")
    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
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
}
