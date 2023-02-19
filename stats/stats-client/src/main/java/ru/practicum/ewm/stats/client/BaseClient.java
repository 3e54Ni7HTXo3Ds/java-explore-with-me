package ru.practicum.ewm.stats.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stats.dto.HitResponseDto;

import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<HitResponseDto[]> get(String path, Map<String, Object> parameters) {
        return makeAndSendGetRequest(path, parameters);
    }

    private <T> ResponseEntity<HitResponseDto[]> makeAndSendGetRequest(String path, Map<String, Object> parameters) {
        ResponseEntity<HitResponseDto[]> response;
        try {
            response = rest.getForEntity(path, HitResponseDto[].class, parameters);

        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Network error");
        }
        return prepareGetResponse(response);
    }

    private static ResponseEntity<HitResponseDto[]> prepareGetResponse(
            ResponseEntity<HitResponseDto[]> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }


    protected <T> ResponseEntity<Object> post(T body) {
        return makeAndSendPostRequest(body);
    }

    private <T> ResponseEntity<Object> makeAndSendPostRequest(@Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<Object> response;
        try {
            response = rest.exchange("/hit", HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return preparePostResponse(response);
    }

    private static ResponseEntity<Object> preparePostResponse(ResponseEntity<Object> response) {
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
