package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static br.com.restassuredapitesting.tests.booking.payloads.BookingPayloads.payloadBooking;
import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    @Step("Cria uma Reserva com Header Accept Inválido")
    public Response updateBookingInvalidAcceptHeader() {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "invalid")
                .when()
                .body(payloadBooking().toString())
                .post("booking/");
    }

    @Step("Cria uma Reserva")
    public Response createNewBooking() {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payloadBooking().toString())
                .post("booking/");
    }

    @Step("Cria uma Reserva com mais Parâmetros")
    public Response createNewBookingWithExtraParameter() {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payloadBooking().put("campo 1", "Valor campo 1").toString())
                .post("booking/");
    }

    @Step("Cria uma Reserva com Payload Inválido")
    public Response createNewBookingInvalidPayload() {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payloadBooking().put("bookingdates","Date"))
                .post("booking/");
    }
}
