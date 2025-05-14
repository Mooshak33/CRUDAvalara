package org.crud.service;

import org.crud.config.OAuth2ClientProperties;
import org.crud.model.MandateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class MandateService {
     private static final Logger log = LoggerFactory.getLogger(MandateService.class);

    private final RestTemplate restTemplate;
    private final TokenService tokenService;
    private final OAuth2ClientProperties oAuth2ClientProperties;

    public MandateService(RestTemplate restTemplate, TokenService tokenService,
                          OAuth2ClientProperties oAuth2ClientProperties) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }

    public MandateResponse getMandates(HttpHeaders headers, String filter, Integer top, Integer skip) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            String[] parts = filter.split("\\s+(eq|contains)\\s+");
            String fieldName="";
            String operatorWithValue="";
            String operator="";
            String value="";
            if (parts.length == 2) {
                fieldName = parts[0].trim();
                operatorWithValue = filter.substring(fieldName.length()).trim();
                operator = operatorWithValue.split("\\s+")[0];
                value = operatorWithValue.substring(operator.length()).trim();
                // Remove surrounding single quotes if present
                if (value.startsWith("'") && value.endsWith("'") && value.length() > 1) {
                    value = value.substring(1, value.length() - 1);
                }
            }
            URI uri = UriComponentsBuilder.fromHttpUrl("https://api.sbx.avalara.com/einvoicing/mandates")
                    .queryParam(fieldName, value)
                    .queryParam("$top", top)
                    .queryParam("$skip", skip)
                    .build()
                    .toUri();
        ResponseEntity<MandateResponse> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MandateResponse.class
        );
        MandateResponse mandateResponse = response.getBody();
        if (mandateResponse != null) {
             log.info("Successfully fetched mandates: {}", mandateResponse);
            return mandateResponse;
        } else {
              log.error("Failed to fetch mandates, response body is null");
        }
    } catch (Exception e) {
          log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
    }
        return null;
}
}