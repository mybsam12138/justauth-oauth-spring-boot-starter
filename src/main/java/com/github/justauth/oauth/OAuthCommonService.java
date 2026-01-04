package com.github.justauth.oauth;

import com.github.justauth.oauth.enums.OAuthProvider;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;

public interface OAuthCommonService {

    /**
     * 生成授权链接并重定向
     *
     * @return 授权链接
     */
    String generateAuthorizeUrl(String redirectUrl);

    AuthUser getOauthUser(AuthCallback callback);

    String getOauthRedirectUrl(String state);

    /** Each implementation knows its own provider */
    OAuthProvider getProvider();
}