package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.suites.SmokeTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

@Feature("Feature - Retorno de Reservas")
public class GetBookingTest extends BaseTest {
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    PostBookingRequest postBookingRequest = new PostBookingRequest();
    int primeiroId = getBookingRequest.bookingReturnIds().then().extract().path("[0].bookingid");
    Response firstIdInfo = getBookingRequest.returnBookingSpecificId(primeiroId);
    HashMap<String, Object> hashMapDates = firstIdInfo.then().extract().path("bookingdates");
    HashMap<String, Object> hashMapBooking = firstIdInfo.then().extract().path("");


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Listar ID's de reservas")
    public void validateBookingList() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingReturnIds()
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("[0].bookingid", notNullValue());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o schema de retorno da listagem de reservas")
    public void validateBookingListSchema() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingReturnIds()
                .then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "bookings"))));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o schema de retorno da listagem de uma reserva específica")
    public void validateSpecificBookingSchema() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.returnBookingSpecificId(temporaryBookingId)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "specificbooking"))));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro firstname")
    public void validateBookingSearchFirstnameParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingFirstName(hashMapBooking.get("firstname").toString())
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro lastname")
    public void validateBookingSearchLastnameParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingLastname(hashMapBooking.get("lastname").toString())
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkin")
    public void validateBookingSearchCheckinParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckin(hashMapDates.get("checkin"))
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkout")
    public void validateBookingSearchCheckoutParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckout(hashMapDates.get("checkout"))
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkout duas vezes ")
    public void valiateBookingSearchCheckoutTwiceParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckoutTwice(hashMapDates.get("checkout").toString(), "2020-01-01")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando os parâmetros primeiro nome, último sobrenome," +
            " data de checkin e data de checkout")
    public void validateBookingSearchNameCheckinCheckoutParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingNameCheckinCheckout(
                        hashMapBooking.get("firstname"),
                        hashMapBooking.get("lastname"),
                        hashMapDates.get("checkin"),
                        hashMapDates.get("checkout"))
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro na pesquisa com filtro checkin mal formatado")
    public void validateErrorSearchWrongCheckinParameter() {

        getBookingRequest.bookingSearchUsingCheckin("invalid")
                .then().log().ifValidationFails()
                .statusCode(500)
                .body(notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro na pesquisa com filtro firstname mal formatado")
    public void validateErrorSearchWrongFirstnameParameter() {

        getBookingRequest.bookingSearchUsingFirstName("33")
                .then().log().ifValidationFails()
                .statusCode(400)
                .body(notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro na pesquisa com filtro inválido")
    public void validateSearchErrorInvalidParameter() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingInvalidParameter(hashMapDates.get("checkout").toString())
                .then().log().ifValidationFails()
                .statusCode(400)
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken());
    }
}