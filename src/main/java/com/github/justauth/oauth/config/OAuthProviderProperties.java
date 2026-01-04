package com.github.justauth.oauth.config;

import lombok.Data;

@Data
public class OAuthProviderProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}