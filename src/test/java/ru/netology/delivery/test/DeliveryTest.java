package ru.netology.delivery.test;

import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import ru.netology.delivery.data.DataGenerator;


import static ru.netology.delivery.data.DataGenerator.generateDate;

class DeliveryTest {


    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(By.className("button")).click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(secondMeetingDate);
        Selenide.$(By.className("button")).click();
        Selenide.$x("//span[contains(text(),'Перепланировать')]").click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);
    }

    @Test
    @DisplayName("Plan meeting without replan")
    void shouldSuccessfulPlanWithoutReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(By.className("button")).click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
    }

    @Test
    @DisplayName("Planning and replanning a meeting the day before")
    void shouldSuccessfulPlanAndReplanMeetingDayBefore() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 3;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(By.className("button")).click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(secondMeetingDate);
        Selenide.$(By.className("button")).click();
        Selenide.$x("//span[contains(text(),'Перепланировать')]").click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);
    }

    @Test
    @DisplayName("Planning and replanning a meeting for the same day")
    void shouldSuccessfulPlanAndReplanMeetingDaySame() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 4;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(By.className("button")).click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(secondMeetingDate);
        Selenide.$(By.className("button")).click();
        Selenide.$x("//span[contains(text(),'Перепланировать')]").click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible);
    }

    @Test
    @DisplayName("Unsuccessful replanned day")
    void shouldUnsuccessfulReplanMeetingDay() {
        String planningDate = generateDate(1);
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(By.className("button")).click();
        Selenide.$(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible);
        Selenide.$(By.className("button")).click();
        Selenide.$x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        Selenide.$x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        Selenide.$x("//span[contains(text(),'Перепланировать')]").click();
        Selenide.$x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Заказ на выбранную дату невозможен"));
    }
}