package com.klimigo.application.conversation.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IKlimovskiy on 26.5.2017 г..
 */
public class AuthorizationRequest {

    @JsonProperty("client_id")
    String clientId = "9_2echp63zvosgw088gkoo8cgs0s00w8kcco4g80skgw4skg0cww";
    @JsonProperty("client_secret")
    String clientSecret = "6603v1bp3ow8c0k4cs4ko8kok8g4sg84kskcg0coo04oco0c84";
    @JsonProperty("grant_type")
    String grantType = "client_credentials";
    @JsonProperty
    String scope = "api";

}
