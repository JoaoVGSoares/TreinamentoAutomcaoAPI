package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.greaterThan;

public class PostBookingTest extends BaseTest {
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    PostBookingRequest postBookingRequest = new PostBookingRequest();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Alterar uma reserva Header inválido")
    public void validateBookingUpdateErrorWrongHeader() {
        int primeiroId = getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");


        postBookingRequest.updateBookingInvalidHeaderAccept(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(418)
                .log().all();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Criar uma reserva")
    public void createNewBooking() {
        postBookingRequest.createNewBooking()
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(AllTests.class)
    @DisplayName("Criar duas reservas em sequencia")
    public void createNewBookingx2() {
        for (int i = 0; i < 3; i++) {
            i++;
            postBookingRequest.createNewBooking()
                    .then()
                    .statusCode(200)
                    .log().all()
                    .body("size()", greaterThan(0));

        }
    }
}
