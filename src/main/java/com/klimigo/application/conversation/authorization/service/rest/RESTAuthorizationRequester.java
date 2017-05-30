package com.klimigo.application.conversation.authorization.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final HttpEntity<AuthorizationRequest> request;
    private final ObjectMapper objectMapper;
    private String uri = "https://demo-oauth.transferbookingengine.com/oauth/v2/token";
    private RestTemplate template;

    public RESTAuthorizationRequester() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        template = new RestTemplate(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        // TODO: just for testing. late off.
        try {
            System.out.println("Request: \n" + objectMapper.writeValueAsString(authorizationRequest));
        } catch (JsonProcessingException e) {
            // Nothing do
            e.printStackTrace();
        }
        request = new HttpEntity<>(authorizationRequest, headers);
    }

    @Override
    public AuthorizationData requestAuthorizationData() {
        AuthorizationData authorizationData = template.postForEntity(uri, request, AuthorizationData.class).getBody();
        try {
            // TODO: just for testing. late off.
            System.out.println("Response: \n" + objectMapper.writeValueAsString(authorizationData));
        } catch (JsonProcessingException e) {
            // Nothing do
            e.printStackTrace();
        }
        return authorizationData;
    }
}
