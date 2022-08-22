package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldGetSuccesfulNotification() {
        String planningDate = generateDate(4);
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        $x("//input[@placeholder='Город']").val("Рязань").pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().pressEscape().sendKeys(planningDate);
        $x("//input[@name='name']").val("Семин Денис");
        $x("//input[@name='phone']").val("+79109009593");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]").click();
        $x("//div[@class='notification__content']").shouldBe(visible, Duration.ofSeconds(20));
        $x("//div[contains(text(),'Встреча успешно забронирована на ')]").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20)).shouldBe(visible);
    }

}
