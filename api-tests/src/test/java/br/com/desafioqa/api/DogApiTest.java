package br.com.desafioqa.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;

@Epic("Desafio técnico QA")
@Feature("Dog API")
@Tag("api")
class DogApiTest extends BaseApiTest {

    @Test
    @Story("Listagem de raças")
    @Severity(CRITICAL)
    @DisplayName("API-001 - Deve listar as raças disponíveis")
    @Description("Valida status HTTP, status de negócio e que a coleção de raças não está vazia.")
    void deveListarTodasAsRacas() {
        given()
                .spec(requestSpec)
        .when()
                .get("/breeds/list/all")
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message", instanceOf(Map.class))
                .body("message", is(not(anEmptyMap())));
    }

    @Test
    @Story("Imagens por raça")
    @Severity(CRITICAL)
    @DisplayName("API-002 - Deve listar imagens da raça hound")
    @Description("Valida que uma raça existente retorna uma coleção não vazia de imagens coerentes.")
    void deveListarImagensDaRacaHound() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "hound")
        .when()
                .get("/breed/{breed}/images")
        .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", instanceOf(List.class))
                .body("message", is(not(empty())))
                .body("message[0]", containsString("/breeds/hound-"))
                .body("message[0]", matchesPattern("^https://.+\\.(jpg|jpeg|png)$"));
    }

    @Test
    @Story("Imagem aleatória")
    @Severity(CRITICAL)
    @DisplayName("API-003 - Deve retornar uma imagem aleatória")
    @Description("Valida o contrato mínimo e o formato da URL retornada.")
    void deveRetornarImagemAleatoria() {
        given()
                .spec(requestSpec)
        .when()
                .get("/breeds/image/random")
        .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", notNullValue())
                .body("message", matchesPattern("^https://.+\\.(jpg|jpeg|png)$"));
    }

    @Test
    @Story("Tratamento de erro")
    @Severity(NORMAL)
    @DisplayName("API-004 - Deve rejeitar raça inexistente")
    @Description("Valida status HTTP 404 e o contrato de erro para uma raça inválida.")
    void deveRejeitarRacaInexistente() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "qaautomacao999999")
        .when()
                .get("/breed/{breed}/images")
        .then()
                .statusCode(404)
                .contentType("application/json")
                .body("status", equalTo("error"))
                .body("code", equalTo(404))
                .body("message", not(emptyOrNullString()));
    }

    @Test
    @Story("Contrato")
    @Severity(NORMAL)
    @DisplayName("API-005 - Deve respeitar os schemas dos endpoints obrigatórios")
    void deveRespeitarOsContratosDosEndpointsObrigatorios() {
        given().spec(requestSpec)
                .when().get("/breeds/list/all")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/breeds-list-schema.json"));

        given().spec(requestSpec).pathParam("breed", "hound")
                .when().get("/breed/{breed}/images")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/images-list-schema.json"));

        given().spec(requestSpec)
                .when().get("/breeds/image/random")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/random-image-schema.json"));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {
            "/breeds/list/all",
            "/breed/hound/images",
            "/breeds/image/random"
    })
    @Story("Content-Type")
    @Severity(NORMAL)
    @DisplayName("API-006 - Deve retornar JSON nos endpoints obrigatórios")
    void deveRetornarJsonNosEndpointsObrigatorios(String endpoint) {
        given()
                .spec(requestSpec)
        .when()
                .get(endpoint)
        .then()
                .statusCode(200)
                .contentType("application/json");
    }
}
