package com.klimigo.application.conversation.authorization.service;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.AuthorizationRequester;
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
        lock.lock();
        try {
            refreshToken();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public AuthorizationToken getToken() throws InterruptedException {
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

    @Override
    public void returnToken() {
        if (counter.get() == 0) {
            // Here should be logging.
        }
        if (0 == counter.decrementAndGet()) {
            synchronized (counter) {
                counter.notifyAll();
            }
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

    protected void refreshToken() throws InterruptedException {
        synchronized (counter) {
            while (counter.get() != 0) {
                counter.wait(requestTimeBefore * 10);
            }
        }
        // refresh token
        AuthorizationData authorizationData = authorizationRequester.requestAuthorizationData();
        token = Objects.isNull(authorizationData) ? null : new AuthorizationToken(authorizationData);
    }

    protected boolean isExpired(AuthorizationToken authorizationToken) {
        if (Objects.isNull(authorizationToken)) {
            return true;
        }
        long currentTime = System.currentTimeMillis() + requestTimeBefore * 1000;
        long expirationTime = authorizationToken.getEndTime();
        return (expirationTime < currentTime);
    }
}
