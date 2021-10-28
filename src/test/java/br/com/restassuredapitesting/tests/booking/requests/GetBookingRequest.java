package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    @Step("Retorna os ID's da Listagem de Reservas")
    public Response bookingReturnIds() {
        return given()
                .when()
                .get("booking");
    }

    @Step("Retorna uma Reserva Específica")
    public Response returnBookingSpecificId(int id) {
        return given()
                .when()
                .get("booking/" + id);
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando o Parâmetro Firstname")
    public Response bookingSearchUsingFirstName(String firstname) {
        return given()
                .queryParams("firstname", firstname)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando o Parâmetro Lastname")
    public Response bookingSearchUsingLastname(String lastname) {
        return given()
                .queryParams("lastname", lastname)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando o Parâmetro Checkin")
    public Response bookingSearchUsingCheckin(Object checkin) {
        return given()
                .queryParams("checkin", checkin)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando o Parâmetro Checkout")
    public Response bookingSearchUsingCheckout(Object checkout) {
        return given()
                .queryParams("checkout", checkout)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando o Parâmetro Checkout Duas Vezes")
    public Response bookingSearchUsingCheckoutTwice(String checkout1, String checkout2) {
        return given()
                .queryParams("checkout", checkout1)
                .queryParams("checkout", checkout2)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando os Parâmetros Firstname, Lastname," +
            " Checkin e Checkout")
    public Response bookingSearchUsingNameCheckinCheckout(Object firstname,
                                                          Object lastname,
                                                          Object checkin,
                                                          Object checkout) {
        return given()
                .queryParams("firstname", firstname, "lastname", lastname,
                        "checkin", checkin, "checkout", checkout)
                .when()
                .get("booking");
    }

    @Step("Retorna a Pesquisa das Reservas Utilizando Parâmetro Inválido")
    public Response bookingSearchUsingInvalidParameter(String name) {
        return given()
                .queryParams("invalid", name)
                .when()
                .get("booking");
    }
}
