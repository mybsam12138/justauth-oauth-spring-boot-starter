package com.github.justauth.oauth.controller;

import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import com.github.justauth.oauth.strategy.OAuthStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final OAuthStrategyFactory oAuthStrategyFactory;

    @RequestMapping("/{oauthProvider}")
    public String genAuthorizeUrl(@RequestParam String redirectUrl,@PathVariable OAuthProvider oauthProvider){
        OAuthCommonService oAuthCommonService = oAuthStrategyFactory.get(oauthProvider);
        String url = oAuthCommonService.generateAuthorizeUrl(redirectUrl);
        return url;
    }
}
