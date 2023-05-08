package ru.rikmasters.car;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class BaseClient {
    private final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> void patch(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse = rest.exchange(path, HttpMethod.PUT, requestEntity, Object.class);
        prepareGatewayResponse(statsServerResponse);
    }

    protected <T> void delete(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse = rest.exchange(path, HttpMethod.DELETE, requestEntity, Object.class);
        prepareGatewayResponse(statsServerResponse);
    }

    private static void prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            responseBuilder.body(response.getBody());
            return;
        }
        responseBuilder.build();
    }


}
