package br.com.restassuredapitesting.tests.auth.payloads;

import org.json.JSONObject;

public class AuthPayloads {
    public JSONObject jsonTokenLogin() {
        JSONObject payloadLogin = new JSONObject();
        payloadLogin.put("username", "admin");
        payloadLogin.put("password", "password123");

        return payloadLogin;
    }
}
