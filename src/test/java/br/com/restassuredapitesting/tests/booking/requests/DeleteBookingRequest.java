package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    @Step("Deleta uma Reserva Específica com o Parâmetro Token")
    public Response deleteBookingUsingToken(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", token)
                .when()
                .delete("booking/" + id);

    }

    @Step("Deleta uma Reserva Específica com o Parâmetro Basic Auth")
    public Response deleteBookingUsingBasicAuth(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorisation", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("booking/" + id);

    }
}
