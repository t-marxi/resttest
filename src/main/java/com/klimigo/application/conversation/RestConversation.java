package com.klimigo.application.conversation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klimigo.application.conversation.authorization.service.AuthorizationService;
import com.klimigo.application.conversation.authorization.service.rest.RESTAuthorizationRequester;
import com.klimigo.application.conversation.authorization.token.AuthorizationToken;

/**
 * Created by IKlimovskiy on 25.5.2017 г..
 */
public class RestConversation {

    public static void main(String[] args) throws JsonProcessingException {
//        String uri = "https://demo-oauth.transferbookingengine.com/oauth/v2/token";
//        RestTemplate template = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
//        HttpEntity<AuthorizationRequest> request = new HttpEntity<AuthorizationRequest>(authorizationRequest, headers);
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//        messageConverters.add(new FormHttpMessageConverter());
//        template.setMessageConverters(messageConverters);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        System.out.println("Request: \n" + objectMapper.writeValueAsString(authorizationRequest));
//        AuthorizationData authorizationData = template.postForEntity(uri, request, AuthorizationData.class).getBody();
//        System.out.println("Response: \n" + objectMapper.writeValueAsString(authorizationData));

        AuthorizationService service = new AuthorizationService();
        service.setAuthorizationRequester(new RESTAuthorizationRequester());
        service.setRequestTimeBefore(3590);
        for (int i = 0; i < 10; ++i) {
            new Thread(() ->
            {
                while (true) {
                    try {
                        AuthorizationToken token = service.getToken();
                        System.out.println(Thread.currentThread().getName() + ":\n " + token);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        service.returnToken();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            ).start();
        }
    }
}
