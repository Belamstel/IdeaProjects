package Api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static helpers.LoadCredentials.getCredentialsFromJson;
import static io.qameta.allure.Allure.parameter;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiSteps {

    @Step("Проверяем созданную Issue")
    public void shouldSeeIssueWithNumber(final String ISSUE_NUMBER, final String ISSUE_TITLE, final String ISSUE_TEXT) {
        parameter("Номер задачи", ISSUE_NUMBER);

        String gitHubToken = "";
        String baseUrl = "https://api.github.com";
        String baseUrlPath = "/repos/belamstel/qa_guruProjects/issues";
        gitHubToken = getCredentialsFromJson("ApiTests.secret", "GitHub");

        Issue issue = new Issue();
        issue = (Issue) given()
                .filter(new AllureRestAssured())
                .header("Authorization", "token " + gitHubToken)
                .baseUri("https://api.github.com")
                .when()
                .get("/repos/belamstel/qa_guruProjects/issues/" + ISSUE_NUMBER)
                .then()
                .statusCode(200)
                .body("number", equalTo(ISSUE_NUMBER))
                .body("title", equalTo(ISSUE_TITLE))
                .body("body",equalTo(ISSUE_TEXT));
    }
}
