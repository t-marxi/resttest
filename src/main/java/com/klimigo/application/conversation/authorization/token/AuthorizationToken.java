package com.klimigo.application.conversation.authorization.token;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.TimeUtils;
import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class AuthorizationToken {
    private final String token;
    private final long startTime;
    private final int timeInSec;
    private final long endTime;
    private final String tokenType;

    public AuthorizationToken(String token, long startTime, int timeInSec, long endTime, String tokenType) {
        this.token = token;
        this.startTime = startTime;
        this.timeInSec = timeInSec;
        this.endTime = endTime;
        this.tokenType = tokenType;
    }

    public AuthorizationToken(AuthorizationData data) {
        this.token = data.getAccessToken();
        this.timeInSec = data.getExpiresIn();
        this.startTime = System.currentTimeMillis();
        this.endTime = TimeUtils.countTimePlusSeconds(this.startTime, timeInSec);
        this.tokenType = data.getTokenType();
    }

    public String getToken() {
        return token;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTimeInSec() {
        return timeInSec;
    }


    public long getEndTime() {
        return endTime;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "AuthorizationToken{" +
                "token='" + token + '\'' +
                ", startTime=" + startTime +
                ", timeInSec=" + timeInSec +
                ", endTime=" + endTime +
                '}';
    }
}
