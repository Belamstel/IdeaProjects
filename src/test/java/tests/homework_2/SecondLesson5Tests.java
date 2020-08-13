package tests.secondHW;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SecondLesson5Tests {
    @Test
    void bannerCovidTest() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        
        // Проверяем, что на главной странице есть баннер по COVID
        $(".banner-info-virus").shouldBe(visible);
    }

    @Test
    void successLoginWithESIATest() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        
        $(byText("Личный кабинет")).click();
        $(byText("Войти через ЕСИА")).click();
        
        // Проверяем что по клику совершается переход на сайт Госуслуг
        $(".baseheader").shouldHave(text("Единая система идентификации и аутентификации"));
        assertTrue(url().contains("https://esia.gosuslugi.ru/"), url());
        
        // Заполнить форму авторизации
        
        // Проверить успешную авторизацию и редирект
    }

    @Test
    void searchMedicineTest() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        
        // Проверям работоспособность поиска лекарственных средств
        $(byText("Наличие льготных лекарственных средств в аптеках")).click();
//        $(by("placeholder", "Поиск по номеру рецепта или наименованию ЛС")).setValue("Резорба").pressEnter();
       
        //Проверяем, что в таблице есть препарат
        $(".pills__table").shouldHave(text("Резорба"));
    }

    @Test
    void callDoctorForEsiaOnlyTest() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        
        $(byText("Вызов врача на дом")).click();
        
        // Проверяем что возможность вызова врача есть только для авторизованного пользователя
        $(byText("Войти через ЕСИА")).should(visible);
    }

    @Test
    void findMedicineByApiTest() {
        RestAssured.baseURI = "http://new.2dr.ru";
        Response response = RestAssured
        .given()
                .log().body()
                .contentType(ContentType.JSON)
                .queryParam("search","резорба")
                .queryParam("limit","10")
                .queryParam("offset","0")
        .when()
                .get("/api/medicines")
        .then()
                .statusCode(200)
                .extract()
                .response();
        
        String jsonString = response.asString();
        assertTrue(jsonString.contains("Резорба"), jsonString);
    }
}
