package br.com.desafioqa.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setUpApi() {
        String baseUrl = System.getProperty("dog.api.baseUrl", "https://dog.ceo/api");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
