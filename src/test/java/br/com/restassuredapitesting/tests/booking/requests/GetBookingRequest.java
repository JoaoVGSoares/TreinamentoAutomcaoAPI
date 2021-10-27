package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {


    @Step("Retorna os Ids da Listagem de Reservas")
    public Response bookingReturnIds() {
        return given()
                .when()
                .get("booking");
    }

    @Step("Retorna uma reserva especifica")
    public Response returnBookingSpecificId(int id) {
        return given()
                .when()
                .get("booking/" + id);
    }

    @Step("Retorna a pesquisa das reservas utilizando o parâmetro firstname")
    public Response bookingSearchUsingFirstName(String firstname) {
        return given()
                .queryParams("firstname", firstname)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando o parâmetro lastname")
    public Response bookingSearchUsingLastname(String lastname) {
        return given()
                .queryParams("lastname", lastname)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando o parâmetro checkin")
    public Response bookingSearchUsingCheckin(Object checkin) {
        return given()
                .queryParams("checkin", checkin)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando o parâmetro checkout")
    public Response bookingSearchUsingCheckout(Object checkout) {
        return given()
                .queryParams("checkout", checkout)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando o parâmetro duas vezes")
    public Response bookingSearchUsingCheckoutTwice(String checkout1, String checkout2) {
        return given()
                .queryParams("checkout", checkout1)
                .queryParams("checkout", checkout2)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando os parâmetros firstname, lastname," +
            " checkin e checkout")
    public Response bookingSearchUsingNameCheckinCheckout(Object firstname,
                                                          Object lastname,
                                                          Object checkin,
                                                          Object checkout) {
        return given()
                .queryParams("firstname", firstname, "lastname", lastname,
                        "checkin", checkin, "checkout", checkout)
                .when()
                .log().all()
                .get("booking");
    }

    @Step("Retorna a pesquisa das reservas utilizando parâmetro inválido")
    public Response bookingSearchUsingInvalidParameter(String name) {
        return given()
                .queryParams("invalid", name)
                .when()
                .log().all()
                .get("booking");
    }
}
