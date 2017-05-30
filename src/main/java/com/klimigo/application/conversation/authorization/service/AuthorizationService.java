package com.klimigo.application.conversation.authorization.service;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.AuthorizationRequester;
import com.klimigo.application.conversation.authorization.TimeUtils;
import com.klimigo.application.conversation.authorization.TokenProvider;
import com.klimigo.application.conversation.authorization.token.AuthorizationToken;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AuthorizationService implements TokenProvider {

    private volatile AtomicInteger counter = new AtomicInteger();
    private Lock lock = new ReentrantLock();
    private volatile AuthorizationToken token;
    private int requestTimeBefore;
    private AuthorizationRequester authorizationRequester;

    public void init() throws InterruptedException {
        refreshToken();
    }

    @Override
    public AuthorizationToken getToken() {
        lock.lock();
        try {
            if (isExpired(token)) {
                refreshToken();
            }
            counter.incrementAndGet();
            return token;
        } finally {
            lock.unlock();
        }
    }

    public int getRequestTimeBefore() {
        return requestTimeBefore;
    }

    public void setRequestTimeBefore(int requestTimeBefore) {
        this.requestTimeBefore = requestTimeBefore;
    }

    public void setAuthorizationRequester(AuthorizationRequester authorizationRequester) {
        this.authorizationRequester = authorizationRequester;
    }

    protected void refreshToken() {
        lock.lock();
        try {
            AuthorizationData authorizationData = authorizationRequester.requestAuthorizationData();
            token = new AuthorizationToken(authorizationData);
        } finally {
            lock.unlock();
        }
    }

    protected boolean isExpired(AuthorizationToken authorizationToken) {
        if (Objects.isNull(authorizationToken)) {
            return true;
        }
        long currentTime = TimeUtils.getCurrentTimePlus(requestTimeBefore);
        long expirationTime = authorizationToken.getEndTime();
        return (expirationTime < currentTime);
    }
}
