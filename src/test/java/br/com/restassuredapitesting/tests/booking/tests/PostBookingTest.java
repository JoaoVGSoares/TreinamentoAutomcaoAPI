package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.SmokeTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.*;

@Feature("Feature - Alterção de Reservas")
public class PostBookingTest extends BaseTest {
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    PostBookingRequest postBookingRequest = new PostBookingRequest();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Alterar uma reserva com header inválido")
    public void validateBookingUpdateErrorWrongHeader() {
        int temporaryBookingId = postBookingRequest.createNewBooking()
                .then().statusCode(200).log().ifValidationFails().extract().path("bookingid");

        postBookingRequest.updateBookingInvalidAcceptHeader(temporaryBookingId, postAuthRequest.getToken())
                .then()
                .statusCode(418)
                .log().ifValidationFails()
                .body(not(nullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Criar uma reserva")
    public void createNewBooking() {
        postBookingRequest.createNewBooking()
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Criar duas reservas em sequência")
    public void createNewBookingTwice() {
        for(int i=0;i<2;i++) {
            postBookingRequest.createNewBooking()
                    .then()
                    .statusCode(200)
                    .log().ifValidationFails()
                    .body("size()", greaterThan(0))
                    .log().ifValidationFails();
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Criar uma reserva com mais parâmetros")
    public void validateCreateNewBookingWithExtraParameter() {
        postBookingRequest.createNewBookingWithExtraParameter()
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("booking.campo1",hasItem("Valor campo 1"))
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro ao criar reserva com payload inválido")
    public void validateErrorCreateBookingInvalidPayload() {
        postBookingRequest.createNewBookingInvalidPayload()
                .then()
                .statusCode(400)
                .log().ifValidationFails()
                .body(not(nullValue()));
    }
}
