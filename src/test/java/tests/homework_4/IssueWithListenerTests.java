package tests.homework_4;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.ApiSteps;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static steps.Model.NamedBy.css;
import static steps.helpers.LoadCredentials.getCredentialsFromJson;

@Feature("Работа с задачами")
public class IssueWithListenerTests {
    private static final String BASE_URL = "https://github.com/";
    private static final String REPOSITORY = "belamstel/qa_guruProjects";
    private static final String LOGIN = getCredentialsFromJson("Credentials.json", "login");
    private static final String PASSWORD = getCredentialsFromJson("Credentials.json", "pass");
    private static final String ISSUE_TITLE = "Homework 4";
    private static final String ISSUE_TEXT = "test";
    private final ApiSteps apiSteps = new ApiSteps();

    @BeforeAll
    public static void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Создание Issue и проверка по номеру")
    public void withNamedBy() {
        open(BASE_URL);
        $(byText("Sign in")).click();
        $(css("#login_field")
                .as("Логин")).setValue(LOGIN);
        $(css("#password")
                .as("Пароль")).setValue(PASSWORD);
        $(byName("commit")).click();

        open(BASE_URL + REPOSITORY);
        $(byText("Issues")).click();
        $(byText("New Issue")).click();
        $(css("#issue_title")
                .as("Заголовок")).setValue(ISSUE_TITLE);
        $(css("#issue_body")
                .as("Комментарий")).setValue(ISSUE_TEXT);
        $(byText("Submit new issue")).click();
        String number = $x("//span[contains(text(),'#')]").getText().replace("#", "");
        apiSteps.shouldSeeIssueWithNumber(number, ISSUE_TITLE, ISSUE_TEXT);
    }
}

