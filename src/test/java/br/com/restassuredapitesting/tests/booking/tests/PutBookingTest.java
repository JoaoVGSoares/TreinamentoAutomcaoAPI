package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.SecurityTests;
import br.com.restassuredapitesting.suites.SmokeTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PutBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

@Feature("Feature - Atualização de Reservas")
public class PutBookingTest extends BaseTest {
    PostBookingRequest newBooking = new PostBookingRequest();
    PutBookingRequest putBookingRequest = new PutBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();
    int primeiroId = getBookingRequest.bookingReturnIds().then().statusCode(200).extract().path("[0].bookingid");
    Response firstIdInfo = getBookingRequest.returnBookingSpecificId(primeiroId);
    HashMap<String, Object> hashMapBooking = firstIdInfo.then().extract().path("");


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Alterar uma reserva utilizando o token")
    public void validateBookingUpdateTokenParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        String firstname = hashMapBooking.get("firstname").toString();
        String lastname = hashMapBooking.get("lastname").toString();

        putBookingRequest.updateBookingToken(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body(not(contains(firstname,lastname)))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SecurityTests.class})
    @DisplayName("Alterar uma reserva sem enviar o token")
    public void validateErrorBookingUpdateWithoutTokenParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        putBookingRequest.updateBookingWithoutToken(primeiroId)
                .then()
                .statusCode(403).log()
                .ifValidationFails()
                .body(not(nullValue()));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Alterar uma reserva com basic auth")
    public void validateBookingUpdateBasicAuthParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().log().ifError().extract().path("bookingid");

        String firstname = hashMapBooking.get("firstname").toString();
        String lastname = hashMapBooking.get("lastname").toString();

        putBookingRequest.updateBookingBasicAuth(primeiroId, postAuthRequest.getAuth())
                .then()
                .statusCode(200).log().ifValidationFails()
                .body(not(contains(firstname,lastname)))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Alterar uma reserva inexistente")
    public void validateErrorInvalidBookingUpdate() {
        int temporaryBookingId = newBooking.createNewBooking().then().log().ifError().extract().path("bookingid");
        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

        putBookingRequest.updateBookingToken(temporaryBookingId,postAuthRequest.getToken())
                .then()
                .statusCode(405)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SecurityTests.class})
    @DisplayName("Alterar uma reserva com o parâmetro token inválido")
    public void validateErrorBookingUpdateInvalidToken() {
        int temporaryBookingId = newBooking.createNewBooking().then().log().ifError().extract().path("bookingid");
        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

        putBookingRequest.updateBookingInvalidToken(temporaryBookingId)
                .then()
                .statusCode(403)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }
}
