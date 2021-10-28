package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.SecurityTests;
import br.com.restassuredapitesting.suites.SmokeTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.*;

@Feature("Feature - Exclusão de Reservas")
public class DeleteBookingTest extends BaseTest {
    PostBookingRequest newBooking = new PostBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();



    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar exclusão de reserva do booking ")
    public void deleteBooking() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken())
                .then()
                .statusCode(201)
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar erro na exclusão ao utilizar ID inválido ")
    public void deleteInvalidBooking() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");
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
    @Category({AllTests.class, SecurityTests.class})
    @DisplayName("Validar erro na exclusão ao utilizar token inválido ")
    public void validateErrorBookingDeleteInvalidToken() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        deleteBooking.deleteBookingUsingInvalidToken(temporaryBookingId)
                .then()
                .statusCode(403)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar exclusão da reserva no booking com parâmetro basic auth")
    public void validateBookingDeleteUsingBasicAuth() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        deleteBooking.deleteBookingUsingBasicAuth(temporaryBookingId, postAuthRequest.getAuth())
                .then()
                .statusCode(201)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }
}