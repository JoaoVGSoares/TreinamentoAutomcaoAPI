package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Atualiza uma Reserva com Header Accept Inválido")
    public Response updateBookingInvalidAcceptHeader(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "invalid")
                .header("Cookie", token)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Cria uma Reserva")
    public Response createNewBooking() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .post("booking/");
    }

    @Step("Cria uma Reserva com mais Parâmetros")
    public Response createNewBookingWithExtraParameter() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPayloads.payloadExtraParameter().toString())
                .post("booking/");
    }

    @Step("Cria uma Reserva com Payload Inválido")
    public Response createNewBookingInvalidPayload() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body("Nopayload")
                .post("booking/");
    }
}
