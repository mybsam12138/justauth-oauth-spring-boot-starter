package com.github.justauth.oauth.strategy;

import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OAuthStrategyFactory {
    private final Map<OAuthProvider, OAuthCommonService> strategyMap;

    public OAuthStrategyFactory(List<OAuthCommonService> services) {
        this.strategyMap = services.stream()
                .collect(Collectors.toMap(
                        OAuthCommonService::getProvider,
                        Function.identity()
                ));
    }

    public OAuthCommonService get(OAuthProvider provider) {
        return strategyMap.get(provider);
    }
}