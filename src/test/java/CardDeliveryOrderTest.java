import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.commands.IsDisplayed;
import com.github.javafaker.Faker;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CardDeliveryOrderTest {

    @Test
    public void shouldFakerTest() throws IOException {
        open("http://localhost:9999"); // open webpage

        //get data
        DataGenerator generator = new DataGenerator();

        String actualDate = generator.generate("ru").getDate();
        String actualDatePlus = generator.generate("ru").getDate();

        // input and check
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue(generator.generate("ru").getCity()); // input city
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate); // input actualDate
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue(generator.generate("ru").getName()); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue(generator.generate("ru").getPhone()); // input phone
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] [class=\"button__content\"]").findBy(text("Запланировать")).click(); // press order button
        $("[data-test-id=\"success-notification\"] [class=\"notification__content\"]").shouldHave(text("Встреча успешно запланирована на " + actualDate), Duration.ofMillis(15000)); //check registration
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDatePlus); // input another actualDate
        $$("button[class*=\"button\"]").findBy(text("Запланировать")).click(); // press order button
        $("[data-test-id=\"replan-notification\"] [class=\"notification__content\"] [class*=\"button__content\"] [class*=\"button__text\"]").shouldHave(visible, Duration.ofMillis(15000)).click(); //click reorder button
        $("[data-test-id=\"success-notification\"] [class=\"notification__content\"]").shouldHave(text("Встреча успешно запланирована на " + actualDatePlus), Duration.ofMillis(15000)); // check reregistration


    }

}
