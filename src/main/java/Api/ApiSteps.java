package Api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static helpers.LoadCredentials.getCredentialsFromJson;
import static io.restassured.RestAssured.given;

public class ApiSteps {

    private String gitHubToken = "";
    private String baseUrl = "https://api.github.com";
    private String baseUrlPath = "/repos/Belamstel/qa_guruProjects/issues";


    @Step("Проверяем создание задачи")
    public Issue checkCreateIssue() {
        gitHubToken = getCredentialsFromJson("ApiTests.secret", "GitHub");
        // @formatter:off
        return given()
                .filter(new AllureRestAssured())
                .header("Authorization", "token " + gitHubToken)
                .baseUri(baseUrl)
                .when()
                .get(baseUrlPath)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(Issue.class);


        // @formatter:on
    }

}
