package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;


public class DeleteBookingTest extends BaseTest {
    PostBookingRequest newBooking = new PostBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();
    int primeiroId = getBookingRequest.bookingReturnIds().then().statusCode(200).extract().path("[0].bookingid");

    Response firstIdInfo = getBookingRequest.returnBookingSpecificId(primeiroId);
    HashMap<String, Object> hashMapBooking = firstIdInfo.then().extract().path("");


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar exçlusão de reserva do booking ")
    public void deleteBooking() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken())
                .then()
                .statusCode(201)
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar erro na exclusão ao utiliar ID inválido ")
    public void deleteInvalidBooking() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");
        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken())
                .then()
                .statusCode(405)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar erro na exclusão ao utilizar Token inválido ")
    public void validateErrorBookingDeleteInvalidToken() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, "invalid")
                .then()
                .statusCode(403)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar exclusão da reserva no booking com parâmetro Basic Auth")
    public void validateBookingDeleteUsingBasicAuth() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        deleteBooking.deleteBookingUsingBasicAuth(temporaryBookingId)
                .then()
                .statusCode(201)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }
}