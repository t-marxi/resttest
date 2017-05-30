package com.klimigo.application.conversation.authorization;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.klimigo.application.conversation.authorization.token.AuthorizationToken;


public interface TokenProvider {

    AuthorizationToken getToken() throws InterruptedException, JsonProcessingException;

    void returnToken();
}
