package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Atualiza uma Reserva Específica com o Parâmetro Token")
    public Response updateBookingToken(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", token)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);


    }

    @Step("Atualiza uma Reserva Específica sem o Parâmetro Token")
    public Response updateBookingWithoutToken(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Atualiza uma Reserva com o Parâmetro Basic Auth")
    public Response updateBookingBasicAuth(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorisation", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }

    @Step("Atualiza uma Reserva Inexistente com o Parâmetro Token")
    public Response updateInexistentBookingToken(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", token)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);
    }
}