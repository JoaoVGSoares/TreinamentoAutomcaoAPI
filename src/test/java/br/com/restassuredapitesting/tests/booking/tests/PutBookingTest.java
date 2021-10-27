package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
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
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Alterar uma reserva utilizando o token")
    public void validarAlteracaoDeUmaReservaUtilizandoToken() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        String firstname = hashMapBooking.get("firstname").toString();
        putBookingRequest.updateBookingToken(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body(not(contains(firstname)))
                .body("size()", greaterThan(0)).log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category(AllTests.class)
    @DisplayName("Alterar uma reserva utilizando token inválido")
    public void validarErroNaAlteracaoDeUmaReservaUtilizandoTokenInvalido() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        putBookingRequest.updateBookingWithoutToken(primeiroId)
                .then()
                .statusCode(403).log().ifValidationFails()
                .body(not(nullValue()));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Alterar uma reserva com Basic Auth")
    public void validateErrorBookingUpdate() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        putBookingRequest.updateBookingBasicAuth(primeiroId)
                .then()
                .statusCode(403).log().ifValidationFails()
                .body(not(nullValue())).log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Alterar uma reserva Inexistente")
    public void validateError() {
        int temporaryBookingId = newBooking.createNewBooking().then().statusCode(200).log().ifValidationFails().extract().path("bookingid");
        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

        putBookingRequest.updateInexistentBookingToken(temporaryBookingId,postAuthRequest.getToken())
                .then()
                .statusCode(405).log().ifValidationFails()
                .body(not(nullValue())).log().ifValidationFails();
    }
}
