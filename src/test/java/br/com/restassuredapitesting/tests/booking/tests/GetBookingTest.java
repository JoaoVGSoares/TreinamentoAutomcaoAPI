package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
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
    PostBookingRequest newBooking = new PostBookingRequest();
    int primeiroId = getBookingRequest.bookingReturnIds().then().statusCode(200).extract().path("[0].bookingid");
    Response firstIdInfo = getBookingRequest.returnBookingSpecificId(primeiroId);
    HashMap<String, Object> hashMapDates = firstIdInfo.then().extract().path("bookingdates");
    HashMap<String, Object> hashMapBooking = firstIdInfo.then().extract().path("");


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class})
    @DisplayName("Listar Id's de reservas")
    public void validaListagemDeIdsDasReservas() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

       getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200).
                log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema de retorno da listagem de reservas")
    public void validaSchemaDaListagemDeReservas() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "bookings"))));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema de retorno da listagem de uma reserva específica")
    public void validaSchemaDeUmaReservaEspecifica() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.returnBookingSpecificId(temporaryBookingId)
                .then()
                .statusCode(200)
                .log().all()
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "specificbooking"))));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parametro firstname")
    public void validateBookingSearchFirstnameParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingFirstName(hashMapBooking.get("firstname").toString())
                .then()
                .statusCode(200)
                .log().body()
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parametro lastname")
    public void validateBookingSearchLastnameParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingLastname(hashMapBooking.get("lastname").toString())
                .then()
                .statusCode(200)
                .log().body();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parametro checkin")
    public void validaPesquisaComParametroCheckin() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckin(hashMapDates.get("checkin"))
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body(containsInAnyOrder(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parametro checkout")
    public void validateBookingSearchCheckoutParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckout(hashMapDates.get("checkout"))
                .then()
                .statusCode(200)
                .log()
                .body().content(containsInAnyOrder(primeiroId));

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parametro checkout duas vezes ")
    public void valiateBookingSearchCheckoutTwiceParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingCheckoutTwice(hashMapDates.get("checkout").toString(),"2020-01-01")
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando os parâmetros primeiro nome, ultimo sobrenome," +
            " data de checkin e data de checkout")
    public void validateBookingSearchNameCheckinCheckoutParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingNameCheckinCheckout(
                        hashMapBooking.get("firstname"), hashMapBooking.get("lastname"),
                        hashMapDates.get("checkin"),
                        hashMapDates.get("checkout"))
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body(contains(primeiroId))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar erro na pesquisa com filtro checkin mal formatado")
    public void validateErrorSearchWrongCheckinParameter() {

        getBookingRequest.bookingSearchUsingCheckin("invalid")
                .then()
                .statusCode(500)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar erro na pesquisa com filtro firstname mal formatado")
    public void validateErrorSearchWrongFirstnameParameter() {

        getBookingRequest.bookingSearchUsingFirstName("33")
                .then()
                .statusCode(500)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Validar erro na pesquisa com filtro inválido")
    public void validateSearchErrorInvalidParameter() {
        int temporaryBookingId = newBooking.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        getBookingRequest.bookingSearchUsingInvalidParameter(hashMapDates.get("checkout").toString())
                .then()
                .statusCode(400)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();

        deleteBooking.deleteBookingUsingToken(temporaryBookingId, postAuthRequest.getToken()).then().log().ifError();
    }
}