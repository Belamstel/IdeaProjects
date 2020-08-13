package tests.homework_4;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static Api.NamedBy.css;
import static Api.NamedBy.named;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Feature("Работа с задачами")
public class IssueWithListenerTest {

    private static final int ISSUE = 12;
    private static final String BASE_URL = "https://github.com";
    private static final String REPOSITORY = "eroshenkoam/allure-example";

    @BeforeEach
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность найти Issue по номеру")
    public void shouldFindIssueByNumber() {
        open(BASE_URL);
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();
        $(By.linkText("eroshenkoam/allure-example")).click();
        $(withText("Issues")).click();
        $(withText("#" + ISSUE)).should(Condition.exist);
    }

    @Test
    @DisplayName("Пользователь должен иметь возможность найти Issue по номеру")
    public void withNamedBy() {
        open(BASE_URL);
        $(css(".header-search-input").as("Поисковая строка в заголовке")).click();
        $(css(".header-search-input").as("Поисковая строка в заголовке")).sendKeys(REPOSITORY);
        $(css(".header-search-input").as("Поисковая строка в заголовке")).submit();
        $(named(By.linkText("eroshenkoam/allure-example")).as("Ссылка на репозиторий")).click();
        $(withText("Issues")).click();
        $(withText("#" + ISSUE)).should(Condition.exist);
    }

}
