package autotests.CDUD;

import autotests.CommonMethod;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuckTest extends CommonMethod {

    @Test(description = "Проверка того, что уточка создана. Материал = rubber")
    @CitrusTest
    public void successfulCreateRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2green", 0.15, "rubber", "quack", "FIXED");
        validateStatusAndSaveId(runner, HttpStatus.OK);
        // + Проверить что утка есть в БД, все параметры совпадают
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка того, что уточка создана. Материал = wood")
    @CitrusTest
    public void successfulCreateWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "2yellow", 0.15, "wood", "quack", "FIXED");
        validateStatusAndSaveId(runner, HttpStatus.OK);
        // + Проверить что утка есть в БД, все параметры совпадают
        deleteDuck(runner, "${duckId}");
    }
}
