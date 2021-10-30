package br.com.restassuredapitesting.tests.booking.payloads;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class BookingPayloads {
    public static JSONObject payloadBooking() {
        Faker faker = new Faker();
        JSONObject payload = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");

        payload.put("firstname", faker.name().firstName());
        payload.put("lastname", faker.name().lastName());
        payload.put("totalprice", 111);
        payload.put("depositpaid", true);
        payload.put("bookingdates", bookingDates);
        payload.put("additionalneeds", "Breakfast");


        return payload;
    }
}
