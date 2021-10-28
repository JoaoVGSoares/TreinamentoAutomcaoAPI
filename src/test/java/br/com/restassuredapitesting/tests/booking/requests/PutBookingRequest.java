package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Atualiza uma Reserva Específica Com o Parâmetro Token")
    public Response updateBookingToken(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", token)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Atualiza uma Reserva Específica Sem o Parâmetro Token")
    public Response updateBookingWithoutToken(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Atualiza uma Reserva com o Parâmetro Basic Auth")
    public Response updateBookingBasicAuth(int id, String basicAuth) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", basicAuth)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Atualiza uma Reserva com o Parâmetro Token Inválido")
    public Response updateBookingInvalidToken(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "invalid")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }
}