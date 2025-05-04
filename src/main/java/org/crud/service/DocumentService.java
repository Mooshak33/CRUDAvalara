package org.crud.service;

import org.crud.config.OAuth2ClientProperties;
import org.crud.model.DocumentResponse;
import org.crud.model.DocumentStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    private final RestTemplate restTemplate;
    private final TokenService tokenService;
    private final OAuth2ClientProperties oAuth2ClientProperties;
    public DocumentService(RestTemplate restTemplate, TokenService tokenService,
                           OAuth2ClientProperties oAuth2ClientProperties) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }
    public DocumentResponse getDocument(HttpHeaders headers) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<DocumentResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DocumentResponse.class
            );
            DocumentResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                 log.info("Successfully fetched mandates: {}", documentResponse);
                return documentResponse;
            } else {
                  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
              log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    public DocumentStatusResponse getDocumentStatus(HttpHeaders headers, String id) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<DocumentStatusResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents/" + id + "/status",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DocumentStatusResponse.class
            );
            DocumentStatusResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                 log.info("Successfully fetched mandates: {}", documentResponse);
                return documentResponse;
            } else {
                  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
              log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }



}

