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

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

@Feature("Feature - Retorno de Reservas")
public class GetBookingTest extends BaseTest {
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    DeleteBookingRequest deleteBooking = new DeleteBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    PostBookingRequest newBooking = new PostBookingRequest();


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Listar ID's de reservas")
    public void validateBookingList() {
        int temporaryBookingId = newBooking.createNewBooking()
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
        int temporaryBookingId = newBooking.createNewBooking()
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
        int temporaryBookingId = newBooking.createNewBooking()
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

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String firstname = temporaryBooking.path("booking.firstname");

        getBookingRequest.bookingSearchUsingFirstName(firstname)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(id));

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro lastname")
    public void validateBookingSearchLastnameParameter() {

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String lastname = temporaryBooking.path("booking.lastname");

        getBookingRequest.bookingSearchUsingLastname(lastname)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(id));

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkin")
    public void validateBookingSearchCheckinParameter() {

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String checkin = temporaryBooking.path("booking.bookingdates.checkin");

        getBookingRequest.bookingSearchUsingCheckin(checkin)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(id));

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkout")
    public void validateBookingSearchCheckoutParameter() {

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String checkout = temporaryBooking.path("booking.bookingdates.checkout");

        getBookingRequest.bookingSearchUsingCheckout(checkout)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(id));

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando o parâmetro checkout duas vezes ")
    public void valiateBookingSearchCheckoutTwiceParameter() {

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String checkout = temporaryBooking.path("booking.bookingdates.checkout");

        getBookingRequest.bookingSearchUsingCheckoutTwice(checkout, "2020-01-01")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Validar retorno da pesquisa utilizando os parâmetros primeiro nome, último sobrenome," +
            " data de checkin e data de checkout")
    public void validateBookingSearchNameCheckinCheckoutParameter() {

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String firstname = temporaryBooking.path("booking.firstname");
        String lastname = temporaryBooking.path("booking.lastname");
        String checkin = temporaryBooking.path("booking.bookingdates.checkin");
        String checkout = temporaryBooking.path("booking.bookingdates.checkout");

        getBookingRequest.bookingSearchUsingNameCheckinCheckout(firstname, lastname, checkin,checkout)
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", hasItem(id));

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
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

        Response temporaryBooking = newBooking.createNewBooking();
        int id = temporaryBooking.path("bookingid");
        String checkout = temporaryBooking.path("booking.bookingdates.checkout");

        getBookingRequest.bookingSearchUsingInvalidParameter(checkout)
                .then().log().ifValidationFails()
                .statusCode(400)
                .body(notNullValue());

        deleteBooking.deleteBookingUsingToken(id, postAuthRequest.getToken());
    }
}