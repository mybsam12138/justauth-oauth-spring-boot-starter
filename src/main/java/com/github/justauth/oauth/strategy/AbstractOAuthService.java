package com.github.justauth.oauth.strategy;

import com.github.justauth.oauth.OAuthCommonService;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractOAuthService implements OAuthCommonService {

    protected final Map<String, AuthRequest> authRequestMap;
    protected final RedisTemplate<String, String> redisTemplate;

    private static final long STATE_EXPIRE_MINUTES = 5;
    public static final String REDIS_STATE_PREFIX = "oauth:state:";

    @Override
    public String generateAuthorizeUrl(String redirectUrl) {
        String state = getProvider().name() + ":" + AuthStateUtils.createState();
        AuthRequest authRequest = authRequestMap.get(getProvider().name());
        String authorizeUrl = authRequest.authorize(state);
        String redisKey = getOauthRedisKey(authorizeUrl,state);
        // Save to redis
        redisTemplate.opsForValue().set(
                redisKey, redirectUrl == null ? "" : redirectUrl,
                STATE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        log.info("{} authorizeUrl: {}", getProvider(), authorizeUrl);
        return authorizeUrl;
    }


    public abstract String getOauthRedisKey(String authorizeUrl,String state);

    @Override
    public AuthUser getOauthUser(AuthCallback callback) {
        return (AuthUser) authRequestMap.get(getProvider().name())
                .login(callback)
                .getData();
    }

    @Override
    public String getOauthRedirectUrl(String state) {
        String redisKey = REDIS_STATE_PREFIX + state;
        String url = redisTemplate.opsForValue().get(redisKey);
        redisTemplate.delete(redisKey);
        return url;
    }
}