package net.bilberry.candidate.service.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import java.util.Optional;

import static us.monoid.web.Resty.enc;
import static us.monoid.web.Resty.form;

@Service
@Configuration
public class GithubAuthService implements OAuthService {
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_url=%s&scope=%s&state=%s";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String ACCESS_TOKEN_URI_PARAMS = "code=%s&redirect_uri=%s&client_id=%s&client_secret=%s&state=%s";

    @Value("${github.auth.access.token}")
    private String token;

    @Value("${github.auth.scope}")
    private String scope;

    @Value("${github.auth.redirect_url}")
    private String redirectUrl;

    @Value("${github.auth.client.id}")
    private String clientId;

    @Value("${github.auth.client.secret}")
    private String clientSecret;

    @Override
    public boolean hasAccessToken() {
        return StringUtils.isNotBlank(token);
    }

    @Override
    public String provideAccessToken() {
        return token;
    }

    /**
     * GET https://github.com/login/oauth/authorize
     *
     * client_id	string	Required. The client ID you received from GitHub when you registered.
     * redirect_uri	string	The URL in your app where users will be sent after authorization. See details below about redirect urls.
     * scope	string	A comma separated list of scopes. If not provided, scope defaults to an empty list of scopes for users
     *                  that don’t have a valid token for the app. For users who do already have a valid token for the app,
     *                  the user won’t be shown the OAuth authorization page with the list of scopes.
     *                  Instead, this step of the flow will automatically complete with the same scopes
     *                  that were used last time the user completed the flow.
     * state	string	An unguessable random string. It is used to protect against cross-site request forgery attacks.
     * @return
     */
    @Override
    public String prepareAuthUrl() {
        return String.format(AUTH_URL, clientId, enc(redirectUrl), scope, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * client_id	string	Required. The client ID you received from GitHub when you registered.
     * client_secret	string	Required. The client secret you received from GitHub when you registered.
     * code	string	Required. The code you received as a response to Step 1.
     * redirect_uri	string	The URL in your app where users will be sent after authorization. See details below about redirect urls.
     * state	string	The unguessable random string you optionally provided in Step 1.
     * @param code
     * @return
     */
    @Override
    public Optional<String> requestAccessToken(final String code) {
        assert StringUtils.isNotBlank(code);

        String access_token = null;
        Resty r = new Resty().identifyAsMozilla();
        final String url = String.format(ACCESS_TOKEN_URI_PARAMS, code, enc(redirectUrl), clientId, clientSecret, String.valueOf(System.currentTimeMillis()));

        try {
            JSONResource response = r.json(ACCESS_TOKEN_URL, form(url));
            access_token = response.get("access_token").toString();
            System.out.println(access_token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(access_token);
    }
}
