package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.SmokeTests;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.*;

@Feature("Feature - Criação de Reservas")
public class PostBookingTest extends BaseTest {
    PostBookingRequest postBookingRequest = new PostBookingRequest();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Criar uma reserva com header Accept inválido")
    public void validateCreateBookingErrorWrongHeader() {
                postBookingRequest.updateBookingInvalidAcceptHeader()
                .then().log().ifValidationFails()
                .statusCode(418)
                .body(notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Criar uma reserva")
    public void createNewBooking() {
        postBookingRequest.createNewBooking()
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("booking", hasKey("firstname"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, SmokeTests.class})
    @DisplayName("Criar duas reservas em sequência")
    public void createNewBookingTwice() {
        for(int i=0;i<2;i++) {
            postBookingRequest.createNewBooking()
                    .then().log().ifValidationFails()
                    .statusCode(200)
                    .body("booking", hasKey("firstname"));
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Criar uma reserva com mais parâmetros")
    public void validateCreateNewBookingWithExtraParameter() {
        postBookingRequest.createNewBookingWithExtraParameter()
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("booking",hasKey("campo 1"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Validar erro ao criar reserva com payload inválido")
    public void validateErrorCreateBookingInvalidPayload() {
        postBookingRequest.createNewBookingInvalidPayload()
                .then().log().ifValidationFails()
                .statusCode(500)
                .body(notNullValue());
    }
}
