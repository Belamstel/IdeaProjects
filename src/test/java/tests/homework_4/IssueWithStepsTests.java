package tests.homework_4;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.ApiSteps;
import steps.BasicSteps;

import static steps.helpers.LoadCredentials.getCredentialsFromJson;

public class IssueWithStepsTests {
    private static final String BASE_URL = "https://github.com/";
    private static final String REPOSITORY = "belamstel/qa_guruProjects";
    private static final String LOGIN = getCredentialsFromJson("Credentials.json", "login");
    private static final String PASSWORD = getCredentialsFromJson("Credentials.json", "pass");
    private static final String ISSUE_TITLE ="Homework 4";
    private static final String ISSUE_TEXT = "test";
    private final BasicSteps webSteps = new BasicSteps();
    private final ApiSteps apiSteps = new ApiSteps();

    @BeforeAll
    public void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    @DisplayName("Создание Issue и проверка по номеру")
    public void shouldFindIssueByNumber() {
        webSteps.openMainPage(BASE_URL);
        webSteps.signInGithub(LOGIN, PASSWORD);
        webSteps.searchForRepository(BASE_URL, REPOSITORY);
        String number = webSteps.createNewIssue(ISSUE_TITLE, ISSUE_TEXT);
        apiSteps.shouldSeeIssueWithNumber(number, ISSUE_TITLE,ISSUE_TEXT);
    }

}