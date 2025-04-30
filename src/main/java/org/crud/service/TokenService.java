package org.crud.service;

import org.crud.model.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {


    private String accessToken;
    private long tokenExpiryTime;

    public String getAccessToken(String tokenUrl, String clientId, String clientSecret, String grantType) {

        // Return cached token if it's still valid
        if (accessToken != null && System.currentTimeMillis() < tokenExpiryTime) {
            return accessToken;
        }

        // Create request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        // Build the request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Make the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                requestEntity,
                TokenResponse.class);

        // Cache the token and set expiry time (assuming 1 hour expiry)
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            this.accessToken = response.getBody().getAccessToken();
            this.tokenExpiryTime = System.currentTimeMillis() + (response.getBody().getExpiresIn() * 1000);
            return this.accessToken;
        }

        throw new RuntimeException("Failed to obtain access token");
    }
}