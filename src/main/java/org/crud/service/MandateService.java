package org.crud.service;

import org.crud.model.MandateResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MandateService {

    private final RestTemplate restTemplate;

    public MandateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MandateResponse getMandates(HttpHeaders headers) {
        try {
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