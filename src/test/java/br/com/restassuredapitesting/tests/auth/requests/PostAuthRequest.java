package br.com.restassuredapitesting.tests.auth.requests;

import br.com.restassuredapitesting.tests.auth.payloads.AuthPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class PostAuthRequest {
    AuthPayloads authPayloads = new AuthPayloads();

    @Step("Retorna o Token")
    public Response tokenReturn() {
        return given()
                .header("Content-Type", "application/json")
                .body(authPayloads.jsonTokenLogin().toString())
                .post("auth");
    }

    @Step("Busca o Token")
    public String getToken() {
        return "token=" + this.tokenReturn()
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Step("Busca o Basic Auth")
    public String getAuth() {
        return "Basic YWRtaW46cGFzc3dvcmQxMjM=";
    }
}
