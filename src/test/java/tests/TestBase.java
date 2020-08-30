package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static org.openqa.selenium.logging.LogType.*;
import static steps.helpers.AttachmentsHelper.*;


public class TestBase {

    @BeforeAll
    @Step("Tests setup")
    public static void beforeAll() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.headless = true;
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("getBrowserConsoleLogs console logs", getBrowserConsoleLogs());
        attachAsText("getClientConsoleLogs console logs", getClientConsoleLogs());
        attachAsText("getDriverConsoleLogs console logs", getDriverConsoleLogs());

        closeWebDriver();
    }

    public static String getBrowserConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }

    public static String getClientConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(CLIENT));
    }

    public static String getDriverConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(DRIVER));
    }
}