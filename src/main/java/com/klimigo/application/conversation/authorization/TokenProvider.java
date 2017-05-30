package com.klimigo.application.conversation.authorization;


import com.klimigo.application.conversation.authorization.token.AuthorizationToken;


public interface TokenProvider {

    AuthorizationToken getToken();
}
