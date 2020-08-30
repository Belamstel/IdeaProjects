package tests.homework_3;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import static com.codeborne.selenide.CollectionCondition.*;

@Tag("web_tests")
public class AlfabankTests {
    
    @Test
    void archiveDepositTest() {
        // Открываем сайт
        open("https://alfabank.ru/");
        
        // Переходим на страницу с архивными депозитами. Вклады->Депозиты->Архивные депозиты
        $(byTitle("Вклады")).hover();
        $(byTitle("Депозиты")).click();
        $(byText("Архивные депозиты")).click();
        
        // Проверяем что в архие находится 3 депозита
        $("h3").shouldHave(text("Архивные депозиты"));
        $$(".product-cell__cell").shouldHave(size(3));
    }

    @Test
    void depositInsuranceTest() {
        // Открываем сайт и переходим во вклады
        open("https://alfabank.ru/");
        
        //
        $(byTitle("Вклады")).click();
        $(".navigation li").sibling(4).click();
        
        // Проверяем что перешли на нужную страницу
        $("h1").shouldHave(text("Страхование вкладов"));
    }

}
