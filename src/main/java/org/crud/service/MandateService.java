package org.crud.service;

import org.crud.model.MandateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MandateService {

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    @Value("${security.oauth2.client.token-url}")
    private String tokenUrl;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.grant-type}")
    private String grantType;

    public MandateService(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    public MandateResponse getMandates(HttpHeaders headers) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(tokenUrl, clientId, clientSecret,grantType);
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
        ResponseEntity<MandateResponse> response = restTemplate.exchange(
                "https://api.sbx.avalara.com/einvoicing/mandates",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MandateResponse.class
        );
        MandateResponse mandateResponse = response.getBody();
        if (mandateResponse != null) {
            // log.info("Successfully fetched mandates: {}", mandateResponse);
            return mandateResponse;
        } else {
            //  log.error("Failed to fetch mandates, response body is null");
        }
    } catch (Exception e) {
        //  log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
    }
        return null;
}
}