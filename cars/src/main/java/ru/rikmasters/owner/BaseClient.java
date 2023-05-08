package ru.rikmasters.owner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class BaseClient {
    private final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> void setOwner(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse = rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);
        prepareGatewayResponse(statsServerResponse);
    }

    protected <T> void delete(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse = rest.exchange(path, HttpMethod.DELETE, requestEntity, Object.class);
        prepareGatewayResponse(statsServerResponse);
    }

    protected <T> void patch(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse = rest.exchange(path, HttpMethod.PUT, requestEntity, Object.class);
        prepareGatewayResponse(statsServerResponse);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }


}
