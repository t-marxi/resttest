package com.klimigo.application.conversation.authorization.service;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.TimeUtils;
import com.klimigo.application.conversation.authorization.token.AuthorizationToken;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorizationServiceTest {

    // Differents in this parameters shpuld be minimal because test sleeps time of there different (EXPIRES_IN - REQUEST_TIME_BEFORE).
    public static final int REQUEST_TIME_BEFORE = 299;
    public static final int EXPIRES_IN = 300;
    public static final int EXTRA_DELAY = 50;

    @Test
    public void test() {
        AuthorizationService service = new AuthorizationService();
        service.setRequestTimeBefore(REQUEST_TIME_BEFORE);
        assertEquals(REQUEST_TIME_BEFORE, service.getRequestTimeBefore());
        AtomicInteger requestCounter = new AtomicInteger(0);
        service.setAuthorizationRequester(() -> {
            AuthorizationData data = new AuthorizationData();
            data.setAccessToken("test");
            data.setExpiresIn(EXPIRES_IN);
            data.setScope("api");
            data.setTokenType("token_type");
            requestCounter.incrementAndGet();
            System.out.println("new token was requested.");
            return data;
        });
        // check that init call refresh token and load new token.
        service.init();
        assertEquals(1, requestCounter.get());
        // check that token was not reload again after getToken method.
        service.getToken();
        assertEquals(1, requestCounter.get());
        // Wait when new token will be available.
        try {
            Thread.sleep((EXPIRES_IN - REQUEST_TIME_BEFORE) * 1000 + EXTRA_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        // check that request was returned with the correct data and the token was created correctly.
        long start = System.currentTimeMillis();
        AuthorizationToken token = service.getToken();
        long end = System.currentTimeMillis();
        System.out.println("start=" + start
                + "\ntoken start=" + token.getStartTime()
                + "\nend=" + end
                + "\ntoken end=" + token.getEndTime());
        assertTrue((start <= token.getStartTime()) && (token.getStartTime() <= end));
        assertTrue((TimeUtils.countTimePlusSeconds(start, EXPIRES_IN) <= token.getEndTime()) && (token.getEndTime() <= TimeUtils.countTimePlusSeconds(end, EXPIRES_IN)));
        assertEquals(2, requestCounter.get());

        // check that second request was passed to server.
        try {
            Thread.sleep((EXPIRES_IN - REQUEST_TIME_BEFORE) * 1000 + EXTRA_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        service.getToken();
        assertEquals(3, requestCounter.get());
    }

    @Test
    public void testInit() {
        AuthorizationService service = new AuthorizationService();
        service.setRequestTimeBefore(REQUEST_TIME_BEFORE);
        assertEquals(REQUEST_TIME_BEFORE, service.getRequestTimeBefore());
        AtomicInteger requestCounter = new AtomicInteger(0);
        service.setAuthorizationRequester(() -> {
            AuthorizationData data = new AuthorizationData();
            data.setAccessToken("test");
            data.setExpiresIn(EXPIRES_IN);
            data.setScope("api");
            data.setTokenType("token_type");
            requestCounter.incrementAndGet();
            return data;
        });
        // check that request was returned with the correct data and the token was created correctly.
        long start = System.currentTimeMillis();
        AuthorizationToken token = service.getToken();
        long end = System.currentTimeMillis();
        assertTrue((start < token.getStartTime()) && (token.getStartTime() <= end));
        assertTrue((TimeUtils.countTimePlusSeconds(start, EXPIRES_IN) <= token.getEndTime()) && (token.getEndTime() <= TimeUtils.countTimePlusSeconds(end, EXPIRES_IN)));
        assertEquals(1, requestCounter.get());

        // check that second request was passed to server.
        try {
            Thread.sleep((EXPIRES_IN - REQUEST_TIME_BEFORE) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        service.getToken();
        assertEquals(2, requestCounter.get());
    }

}
