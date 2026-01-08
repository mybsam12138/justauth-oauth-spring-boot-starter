package com.github.justauth.oauth.config;

import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthFacebookRequest;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthTwitterRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthRequestConfig {

    private final OAuthProperties props;

    @Bean("OAUTH_GOOGLE")
    @ConditionalOnProperty(prefix = "oauth.providers.oauth_google", name = "client-id")
    public AuthRequest getGoogleRequest() {
        OAuthProviderProperties cfg = props.getProviders().get(OAuthProvider.OAUTH_GOOGLE);

        return new AuthGoogleRequest(AuthConfig.builder()
                .clientId(cfg.getClientId())
                .clientSecret(cfg.getClientSecret())
                .redirectUri(cfg.getRedirectUri())
                .build());
    }

    @Bean("OAUTH_TWITTER")
    @ConditionalOnProperty(prefix = "oauth.providers.oauth_twitter", name = "client-id")
    public AuthRequest getTwitterRequest() {
        OAuthProviderProperties cfg = props.getProviders().get(OAuthProvider.OAUTH_TWITTER);
        ArrayList<String> validScopes = new ArrayList<>();
        validScopes.add("public_profile");
        validScopes.add("email");

        return new AuthTwitterRequest(AuthConfig.builder()
                .clientId(cfg.getClientId())
                .clientSecret(cfg.getClientSecret())
                .redirectUri(cfg.getRedirectUri())
                .scopes(validScopes)
                .build());
    }

    @Bean("OAUTH_FACEBOOK")
    @ConditionalOnProperty(prefix = "oauth.providers.oauth_facebook", name = "client-id")
    public AuthRequest getFacebookRequest() {
        OAuthProviderProperties cfg = props.getProviders().get(OAuthProvider.OAUTH_FACEBOOK);
        ArrayList<String> validScopes = new ArrayList<>();
        validScopes.add("public_profile");

        return new AuthFacebookRequest(AuthConfig.builder()
                .clientId(cfg.getClientId())
                .clientSecret(cfg.getClientSecret())
                .redirectUri(cfg.getRedirectUri())
                .scopes(validScopes)
                .build());
    }

    @Bean("OAUTH_GITHUB")
    @ConditionalOnProperty(prefix = "oauth.providers.oauth_github", name = "client-id")
    public AuthRequest getOauthGithubRequest() {
        OAuthProviderProperties cfg = props.getProviders().get(OAuthProvider.OAUTH_GITHUB);
        ArrayList<String> validScopes = new ArrayList<>();
        validScopes.add("public_profile");

        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(cfg.getClientId())
                .clientSecret(cfg.getClientSecret())
                .redirectUri(cfg.getRedirectUri())
                .scopes(validScopes)
                .build());
    }

    @Bean("OAUTH_GITEE")
    @ConditionalOnProperty(prefix = "oauth.providers.oauth_gitee", name = "client-id")
    public AuthRequest getOauthGiteeRequest() {
        OAuthProviderProperties cfg = props.getProviders().get(OAuthProvider.OAUTH_GITEE);
        ArrayList<String> validScopes = new ArrayList<>();

        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(cfg.getClientId())
                .clientSecret(cfg.getClientSecret())
                .redirectUri(cfg.getRedirectUri())
                .scopes(validScopes)
                .build());
    }
}