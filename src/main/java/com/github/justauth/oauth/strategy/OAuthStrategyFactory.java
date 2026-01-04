package com.github.justauth.oauth.strategy;

import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthStrategyFactory {
    @Autowired
    private Map<OAuthProvider, OAuthCommonService> strategyMap;

    public OAuthCommonService get(OAuthProvider provider) {
        return strategyMap.get(provider);
    }
}