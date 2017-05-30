package com.klimigo.application.conversation.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationData {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "AuthorizationData{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", tokenType='" + tokenType + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
