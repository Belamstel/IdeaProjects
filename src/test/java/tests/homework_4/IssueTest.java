package tests.homework_4;

import Api.Issue;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static helpers.LoadCredentials.getCredentialsFromJson;
import static io.qameta.allure.Allure.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@Owner("ADyachenko")
@Feature("Работа с задачами")

public class IssueTest {
    private static final int ISSUE_NUMBER = 4;
    private static final String BASE_URL = "https://github.com/";
    private static final String REPOSITORY = "qa_guruProjects";
    private static final String LOGIN = getCredentialsFromJson("ApiTests.secret", "login");
    private static final String PASSWORD = getCredentialsFromJson("ApiTests.secret", "pass");

    private static final String ISSUE_TITLE ="Homework 4";
    private static final String ISSUE_TEXT = "test";

    private String gitHubToken = "";
    private Issue issue = new Issue();

    @BeforeEach
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

        @Test
        @DisplayName("Создаем Issue через UI и проверяем по Api")
        public void сreateIssue() {
            link("GitHub", String.format("%s/%s", BASE_URL, REPOSITORY));
            parameter("Репозиторий", REPOSITORY);

            step("Авторизируемся на GitHub", () -> {
                open(BASE_URL);
                $(byText("Sign in")).click();
                $("#login_field").setValue(LOGIN);
                $("#password").setValue(PASSWORD);
                $(byName("commit")).click();
            });

            step("Открываем репозиторий" + REPOSITORY, () -> {
                open(BASE_URL + REPOSITORY);
            });

            step("Переходим на вкладку Issues", () -> {
                $x("//span[text()='Issues']").click();
            });

            step("Создаем новую Issue", () -> {
                $x("//span[text()='New issue']").click();
                $("#issue_title").click();
                $("#issue_title").setValue(ISSUE_TITLE);
                $("#issue_title").click();
                $("#issue_title").setValue(ISSUE_TEXT);
                $(byText("Submit new issue")).click();
            });

             step("Проверяем заполненные поля Issue через API", () -> {
                gitHubToken = getCredentialsFromJson("ApiTests.secret", "GitHub");
                issue = (Issue) given()
                        .filter(new AllureRestAssured())
                        .header("Authorization", "token " + gitHubToken)
                        .baseUri("https://api.github.com")
                        .when()
                        .get("/repos/belamstel/qa_guruProjects/issues/" + ISSUE_NUMBER)
                        .then()
                        .statusCode(200)
                        .body("number", equalTo(ISSUE_NUMBER),
                                 "title", equalTo(ISSUE_TITLE),
                                 "body",equalTo(ISSUE_TEXT));
             });
            }
    }

