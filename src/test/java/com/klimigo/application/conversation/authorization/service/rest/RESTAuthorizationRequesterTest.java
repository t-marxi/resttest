package com.klimigo.application.conversation.authorization.service.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RESTAuthorizationRequesterTest {

    @Test
    public void test() {
        RESTAuthorizationRequester actual = new RESTAuthorizationRequester();
        assertEquals("https://demo-oauth.transferbookingengine.com/oauth/v2/token", actual.getUri());
        String localhost = "http://localhost:8080";
        actual.setUri(localhost);
        assertEquals(localhost, actual.getUri());
    }
}
