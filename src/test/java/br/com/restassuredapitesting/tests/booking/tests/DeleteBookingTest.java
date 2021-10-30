package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
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
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();
    PostBookingRequest newBooking = new PostBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();



    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar exclusão de reserva do booking")
    public void deleteBooking() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().extract().path("bookingid");

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken())
                .then().log().ifValidationFails()
                .statusCode(201)
                .body(notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro na exclusão ao utilizar ID inválido ")
    public void deleteInvalidBookingError() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().extract().path("bookingid");
        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken())
                .then().log().ifValidationFails()
                .statusCode(405)
                .body(notNullValue());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SecurityTests.class})
    @DisplayName("Validar erro na exclusão ao utilizar token inválido ")
    public void validateErrorBookingDeleteInvalidToken() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().extract().path("bookingid");

        deleteBooking.deleteBookingUsingInvalidToken(temporaryBookingId)
                .then().log().ifValidationFails()
                .statusCode(403)
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar exclusão da reserva no booking com parâmetro basic auth")
    public void validateBookingDeleteUsingBasicAuth() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().extract().path("bookingid");

        deleteBooking.deleteBookingUsingBasicAuth(temporaryBookingId, postAuthRequest.getAuth())
                .then().log().ifValidationFails()
                .statusCode(201)
                .body(notNullValue());
    }
}