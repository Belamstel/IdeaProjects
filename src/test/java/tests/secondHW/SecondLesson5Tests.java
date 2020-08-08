package tests.secondHW;



import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;



import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecondLesson5Tests {
    @Test
    void bannerCovid() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        // Проверить, что на главной странице есть баннер по COVID
        $(".banner-info-virus").shouldBe(visible);
    }

    @Test
    void accessLoginESIA() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        $(byText("Личный кабинет")).click();
        $(byText("Войти через ЕСИА")).click();
        // Проверяем что по клику совершается переход на сайт Госуслуг
        sleep(500);
        String actualUrl =url();
        // Проверка что урл содержит необходимый текст. //Тут падает, возможно что то делаю не так
        assertTrue(actualUrl.contains("https://esia.gosuslugi.ru/)") ,"Авторизация через Гослуслуги доступна");
    }

    @Test
    void searchLS() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        // Проверям работоспособность поиска лекарственных средств
        $(byText("Наличие льготных лекарственных средств в аптеках")).click();
        $(byXpath("//input[@placeholder='Поиск по номеру рецепта или наименованию ЛС']")).setValue("Резорба");
        $(".search__icon").click();
        //Проверяем, что в таблице есть препарат
        $(".table").shouldHave(text("Резорба"));

    }

    @Test
    void callDoctorForEsiaOnly() {
        // Открываем портал самозаписи к врачу
        open("http://new.2dr.ru/");
        $(byText("Вызов врача на дом")).click();
        // Проверяем что возможность вызова врача есть только для авторизованного пользователя
        $(byText("Войти через ЕСИА")).should(visible);
    }

    @Test
    void findLsApi() {
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
        assertEquals(jsonString.contains("Резорба"), true);

    }
}
