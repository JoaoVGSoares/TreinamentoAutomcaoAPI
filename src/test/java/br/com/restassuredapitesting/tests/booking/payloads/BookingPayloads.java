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

    public static JSONObject payloadExtraParameter() {
            Faker faker = new Faker();
            JSONObject payloadExtraParameter = new JSONObject();
            JSONObject bookingDates = new JSONObject();
            bookingDates.put("checkin", "2018-01-01");
            bookingDates.put("checkout", "2019-01-01");

            payloadExtraParameter.put("firstname", faker.name().firstName());
            payloadExtraParameter.put("lastname", faker.name().lastName());
            payloadExtraParameter.put("campo1", "Valor campo 1");
            payloadExtraParameter.put("totalprice", 111);
            payloadExtraParameter.put("depositpaid", true);
            payloadExtraParameter.put("bookingdates", bookingDates);
            payloadExtraParameter.put("additionalneeds", "Breakfast");


            return payloadExtraParameter;
        }
}
