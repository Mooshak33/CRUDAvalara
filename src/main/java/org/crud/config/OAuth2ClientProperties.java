package org.crud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.oauth2.client")
@Data
public class OAuth2ClientProperties {

    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String grantType;

}