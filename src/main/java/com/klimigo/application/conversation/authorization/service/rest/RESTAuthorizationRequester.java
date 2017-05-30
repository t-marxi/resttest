package com.klimigo.application.conversation.authorization.service.rest;

import com.klimigo.application.conversation.authorization.AuthorizationData;
import com.klimigo.application.conversation.authorization.AuthorizationRequest;
import com.klimigo.application.conversation.authorization.AuthorizationRequester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RESTAuthorizationRequester implements AuthorizationRequester {

    private static final String DEFAULT_URI = "https://demo-oauth.transferbookingengine.com/oauth/v2/token";

    private final HttpEntity<AuthorizationRequest> request;
    private final RestTemplate template;
    private String uri = DEFAULT_URI;

    public RESTAuthorizationRequester() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        template = new RestTemplate(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        request = new HttpEntity<>(authorizationRequest, headers);
    }

    @Override
    public AuthorizationData requestAuthorizationData() {
        AuthorizationData authorizationData = template.postForEntity(uri, request, AuthorizationData.class).getBody();
        return authorizationData;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
