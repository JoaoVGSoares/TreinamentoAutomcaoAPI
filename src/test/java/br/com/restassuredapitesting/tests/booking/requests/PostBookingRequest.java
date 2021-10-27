package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayloads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {
    BookingPayloads bookingPayloads = new BookingPayloads();

    @Step("Atualiza uma Reserva com Header Accept inválido")
    public Response updateBookingInvalidHeaderAccept(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/jason")
                .header("Cookie", token)
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .put("booking/" + id);

    }

    @Step("Cria uma reserva")
    public Response createNewBooking() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPayloads.payloadBooking().toString())
                .post("booking/");
    }
}
