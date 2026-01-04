package com.github.justauth.oauth.config;

import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {
    private Map<OAuthProvider, OAuthProviderProperties> providers;
}