package ru.netology.domain;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;


import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void Registration(UserInfo registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static UserInfo newActiveUser(String status) {
        String login = faker.name().fullName();
        String password = faker.internet().password();
        UserInfo registration = new UserInfo(login, password, status);
        Registration(registration);
        return registration;

    }

}
