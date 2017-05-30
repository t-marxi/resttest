package com.klimigo.application.conversation.authorization.token;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.TimeUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorizationTokenTest {

    public static final int EXPIRES_IN = 300;

    @Test
    public void test() {
        AuthorizationData data = new AuthorizationData();
        data.setScope("test_scope");
        data.setExpiresIn(EXPIRES_IN);
        data.setAccessToken("access_token");
        data.setTokenType("token_type");
        long start = System.currentTimeMillis();
        AuthorizationToken token = new AuthorizationToken(data);
        long end = System.currentTimeMillis();
        assertTrue((start <= token.getStartTime()) && (token.getStartTime() <= end));
        assertTrue((TimeUtils.countTimePlusSeconds(start, EXPIRES_IN) <= token.getEndTime()) && (token.getEndTime() <= TimeUtils.countTimePlusSeconds(end, EXPIRES_IN)));
        assertEquals(data.getExpiresIn(), token.getTimeInSec());
        assertEquals(data.getAccessToken(), token.getToken());
        assertEquals(data.getTokenType(), token.getTokenType());
    }

}
