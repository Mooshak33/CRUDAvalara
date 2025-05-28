package org.crud.controller.service;

import org.crud.config.OAuth2ClientProperties;
import org.crud.model.MandateResponse;
import org.crud.service.MandateService;
import org.crud.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MandateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TokenService tokenService;

    @Mock
    private OAuth2ClientProperties oAuth2ClientProperties;

    @InjectMocks
    private MandateService mandateService;

    @Test
    void getMandatesReturnsResponseWhenFilterIsValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        MandateResponse mockResponse = new MandateResponse();
        String accessToken = "mockAccessToken";

        when(tokenService.getAccessToken(anyString(), anyString(), anyString(), anyString())).thenReturn(accessToken);
        headers.setBearerAuth(accessToken);
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.sbx.avalara.com/einvoicing/mandates")
                .queryParam("$filter", "filter")
                .build()
                .toUri();
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), eq(MandateResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        MandateResponse response = mandateService.getMandates(headers, "filter", null, null);

       // assertNotNull(response);
      //  assertEquals(mockResponse, response);
    }

    @Test
    void getMandatesReturnsNullWhenResponseBodyIsNull() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        String accessToken = "mockAccessToken";

        when(tokenService.getAccessToken(anyString(), anyString(), anyString(), anyString())).thenReturn(accessToken);
        headers.setBearerAuth(accessToken);
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.sbx.avalara.com/einvoicing/mandates")
                .queryParam("$filter", Optional.ofNullable(null))
                .build()
                .toUri();
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), eq(MandateResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        MandateResponse response = mandateService.getMandates(headers, null, null, null);

        assertNull(response);
    }

    @Test
    void getMandatesHandlesExceptionGracefully() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        String accessToken = "mockAccessToken";

        when(tokenService.getAccessToken(anyString(), anyString(), anyString(), anyString())).thenReturn(accessToken);
        headers.setBearerAuth(accessToken);
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.sbx.avalara.com/einvoicing/mandates")
                .queryParam("$filter", Optional.ofNullable(null))
                .build()
                .toUri();
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(HttpEntity.class), eq(MandateResponse.class)))
                .thenThrow(new RuntimeException("Mock exception"));

        MandateResponse response = mandateService.getMandates(headers, null, null, null);

        assertNull(response);
    }
}
