package com.github.justauth.oauth.strategy;

import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractOauth1Template extends AbstractOAuthService{

    public AbstractOauth1Template(Map<String, AuthRequest> authRequestMap,
            RedisTemplate<String, String> redisTemplate) {
        super(authRequestMap, redisTemplate);
    }

    @Override
    public String getOauthRedisKey(String authorizeUrl,String state){
        String oauthToken = extractOauthToken(authorizeUrl);
        return AbstractOAuthService.REDIS_STATE_PREFIX + oauthToken;
    }

    private String extractOauthToken(String authorizeUrl) {
        String oauthToken = "";  // Initialize oauthToken

        if (authorizeUrl != null && authorizeUrl.contains("oauth_token=")) {
            // Use a regular expression to find the oauth_token=xxxx part
            String regex = "oauth_token=([A-Za-z0-9-_]+)";  // Capture the oauth_token value
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(authorizeUrl);
            if (matcher.find()) {
                oauthToken = matcher.group(1);  // Group 1 will contain the token
            }
        }

        return oauthToken;
    }
}
