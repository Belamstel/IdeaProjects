package steps.Api;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.link;
import static io.qameta.allure.Allure.parameter;

public class BasicSteps {

    @Step("Открываем GitHub")
    public void openMainPage(final String BASE_URL) {
        open(BASE_URL);
    }

    @Step("Авторизуемся в Github")
    public void signInGithub(final String LOGIN,final String PASSWORD) {
        $(byText("Sign in")).click();
        $("#login_field").setValue(LOGIN);
        $("#password").setValue(PASSWORD);
        $(byName("commit")).click();
    }

    @Step("Ищем репозиторий")
    public void searchForRepository(final String name, final String BASE_URL) {
        link("GitHub", String.format("%s/%s", BASE_URL, name));
        parameter("Репозиторий", name);
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(name);
        $(".header-search-input").submit();
    }

    @Step("Создаем новую Issue")
    public String createNewIssue(final String ISSUE_TITLE, final String ISSUE_TEXT) {
        $x("//span[text()='Issues']").click();
        $x("//span[text()='New issue']").click();
        $("#issue_title").click();
        $("#issue_title").setValue(ISSUE_TITLE);
        $("#issue_body").click();
        $("#issue_body").setValue(ISSUE_TEXT);
        $(byText("Submit new issue")).click();
        return $x("//span[contains(text(),'#')]").getText()
                .replace("#", "");
    }

}