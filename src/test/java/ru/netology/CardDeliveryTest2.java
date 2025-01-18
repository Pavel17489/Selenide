package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest2 {

    private String selectDate (int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSendFormSuccessTest2(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Мо");
        $$("span.menu-item__control").findBy(Condition.text("Москва")).click();
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        if (!selectDate(3, "MM").equals(selectDate(7, "MM"))) $("[data-step='1']").click();
        $$("div td").findBy(Condition.text(selectDate(7, "d"))).click();
        String planningDate = selectDate(7,"dd.MM.yyyy");
        $("[data-test-id='name'] input").setValue("Поликарпов Николай");
        $("[data-test-id='phone'] input").setValue("+79515684561");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}

