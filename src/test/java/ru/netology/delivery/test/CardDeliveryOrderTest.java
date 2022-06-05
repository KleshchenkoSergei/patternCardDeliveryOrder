package ru.netology.delivery.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.io.IOException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CardDeliveryOrderTest {

    @Test
    @DisplayName("Should successful plan and replan meeting")
    public void shouldSuccessfulPlanAndReplanMeetingTest() throws IOException {
        open("http://localhost:9999"); // open webpage

        //get data
        var city = DataGenerator.Registration.generateCity("ru");
        var daysToFirstMeeting = 3;
        var upperMeetingDate = 30;
        var firstMeetingDate = DataGenerator.Registration.generateDate(daysToFirstMeeting, upperMeetingDate);
        var daysToSecondMeeting = 7;
        upperMeetingDate = 30;
        var secondMeetingDate = DataGenerator.Registration.generateDate(daysToSecondMeeting, upperMeetingDate);
        var name = DataGenerator.Registration.generateName("ru");
        var phone = DataGenerator.Registration.generatePhone("ru");

        // input and check
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue(city); // input city
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(firstMeetingDate); // input actualDate
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue(name); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue(phone); // input phone
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] [class=\"button__content\"]").findBy(text("Запланировать")).click(); // press order button
        $("[data-test-id=\"success-notification\"] [class=\"notification__content\"]").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofMillis(15000)); //check registration
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(secondMeetingDate); // input another actualDate
        $$("button[class*=\"button\"]").findBy(text("Запланировать")).click(); // press order button
        $("[data-test-id=\"replan-notification\"] [class=\"notification__content\"] [class*=\"button__content\"] [class*=\"button__text\"]").shouldHave(visible, Duration.ofMillis(15000)).click(); //click reorder button
        $("[data-test-id=\"success-notification\"] [class=\"notification__content\"]").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofMillis(15000)); // check reregistration
    }

}
