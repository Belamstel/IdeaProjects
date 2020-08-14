package tests.homework_4;

import steps.Api.ApiSteps;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static steps.helpers.LoadCredentials.getCredentialsFromJson;
import static io.qameta.allure.Allure.*;


@Owner("ADyachenko")
@Feature("Работа с задачами")

public class IssueWithLambdaTests {
    private static final int ISSUE_NUMBER = 4;
    private static final String BASE_URL = "https://github.com/";
    private static final String REPOSITORY = "belamstel/qa_guruProjects";
    private static final String LOGIN = getCredentialsFromJson("Credentials.json", "login");
    private static final String PASSWORD = getCredentialsFromJson("Credentials.json", "pass");
    private static final String ISSUE_TITLE = "Homework 4";
    private static final String ISSUE_TEXT = "test";
    private final ApiSteps apiSteps = new ApiSteps();
    private String number = "";

    @BeforeEach
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Создаем Issue через UI и проверяем по steps.Api")
    public void сreateIssue() {
        link("GitHub");
        parameter("Репозиторий", REPOSITORY);

        step("Авторизуемся на GitHub", () -> {
            open(BASE_URL);
            $(byText("Sign in")).click();
            $("#login_field").setValue(LOGIN);
            $("#password").setValue(PASSWORD);
            $(byName("commit")).click();
        });

        step("Открываем репозиторий" + REPOSITORY, () -> {
            open(BASE_URL + REPOSITORY);
        });

        step("Создаем новую Issue", () -> {
            $x("//span[text()='Issues']").click();
            $x("//span[text()='New issue']").click();
            $("#issue_title").click();
            $("#issue_title").setValue(ISSUE_TITLE);
            $("#issue_body").click();
            $("#issue_body").setValue(ISSUE_TEXT);
            $(byText("Submit new issue")).click();
            number = $x("//span[contains(text(),'#')]").getText()
                    .replace("#", "");
        });

        step("Проверяем заполненные поля Issue через API", () -> {
                 apiSteps.shouldSeeIssueWithNumber(number, ISSUE_TITLE,ISSUE_TEXT);
             });
            }
}

