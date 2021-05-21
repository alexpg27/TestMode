package ru.netology.domain;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestModeTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void presenceOfAnActiveUser() {
        UserInfo userInfo = DataGenerator.newActiveUser("active");
        $("[data-test-id=login] input").setValue(userInfo.getLogin());
        $("[data-test-id=password] input").setValue(userInfo.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void presenceOfAnBlockedUser() {
        UserInfo userInfo = DataGenerator.newActiveUser("blocked");
        $("[data-test-id=login] input").setValue(userInfo.getLogin());
        $("[data-test-id=password] input").setValue(userInfo.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void enteringAnInvalidLogin() {
        UserInfo userInfo = DataGenerator.newActiveUser("active");
        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue(userInfo.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void enteringAnInvalidPassword() {
        UserInfo userInfo = DataGenerator.newActiveUser("active");
        $("[data-test-id=login] input").setValue(userInfo.getLogin());
        $("[data-test-id=password] input").setValue("password");
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    public void emptyFieldsLoginAndPassword() {
        UserInfo userInfo = DataGenerator.newActiveUser("active");
        $("[data-test-id=login] input").setValue("");
        $("[data-test-id=password] input").setValue("");
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id=login] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }
}
