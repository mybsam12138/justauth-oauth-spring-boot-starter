package com.github.justauth.oauth.strategy;

import java.util.Map;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractOauth2Template extends AbstractOAuthService{

    public AbstractOauth2Template(Map<String, AuthRequest> authRequestMap,
            RedisTemplate<String, String> redisTemplate) {
        super(authRequestMap, redisTemplate);
    }

    @Override
    public String getOauthRedisKey(String authorizeUrl,String state){
        return AbstractOAuthService.REDIS_STATE_PREFIX + state;
    }
}
