package com.github.justauth.oauth.strategy;

import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.Map;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class GoogleAuthService extends AbstractOauth2Template{

    public GoogleAuthService(Map<String, AuthRequest> authRequestMap,
            RedisTemplate<String, String> redisTemplate) {
        super(authRequestMap, redisTemplate);
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.OAUTH_GOOGLE;
    }
}
