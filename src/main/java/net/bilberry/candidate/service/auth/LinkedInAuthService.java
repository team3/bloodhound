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
public class LinkedInAuthService implements OAuthService {
    private static final String AUTH_URL = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://www.linkedin.com/uas/oauth2/accessToken";
    private static final String ACCESS_TOKEN_URI_PARAMS = "grant_type=%s&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s";

    @Value("${linkedin.auth.redirect_uri}")
    private String redirectUrl;

    @Value("${linkedin.auth.client.id}")
    private String clientId;

    @Value("${linkedin.auth.client.secret}")
    private String clientSecret;

    @Value("${linkedin.auth.scope}")
    private String scope;

    @Value("${linkedin.auth.grant_type}")
    private String grantType;

    // TODO: temporary solution - storing access token in properties file.
    @Value("${linkedin.auth.access.token}")
    private String accessToken;

    /**
     * https://www.linkedin.com/uas/oauth2/authorization
     * Parameter	Description	Required
     * response_type	The value of this field should always be:  code	Yes
     * client_id	The "API Key" value generated when you registered your application.	Yes
     * redirect_uri The URI your users will be sent back to after authorization.  This value must match one of the defined OAuth 2.0 Redirect URLs in your application configuration. e.g. https://www.example.com/auth/linkedin Yes
     * state A unique string value of your choice that is hard to guess. Used to prevent CSRF.e.g. state=DCEeFWf45A53sdfKef424Yes
     * scope
     * A URL-encoded, space delimited list of member permissions your application is requesting on behalf of the user.  If you do not specify a scope in your call, we will fall back to using the default member permissions you defined in your application configuration.
     *
     * e.g. scope=r_fullprofile%20r_emailaddress%20w_share
     *
     * See Understanding application permissions and the Best practices guide for additional information about scopes. Optional
     * @return
     */
    public String prepareAuthUrl() {
        return String.format(AUTH_URL, clientId, enc(redirectUrl), String.valueOf(System.currentTimeMillis()), scope);
    }

    /**
     * https://www.linkedin.com/uas/oauth2/accessToken
     * Parameter	Description	Required
     * grant_type	The value of this field should always be:  authorization_code Yes
     * code	The authorization code you received from Step 2.Yes
     * redirect_uri	The same 'redirect_uri' value that you passed in the previous step.Yes
     * client_id	The "API Key" value generated Step 1.Yes
     * client_secret The "Secret Key" value generated in Step 1. Follow the Best Practices guide for handing your client_secret value. Yes
     * @param code
     * @return
     */
    public Optional<String> requestAccessToken(final String code) {
        assert StringUtils.isNotBlank(code);

        String access_token = null;
        Resty r = new Resty().identifyAsMozilla();
        final String url = String.format(ACCESS_TOKEN_URI_PARAMS, grantType, code, enc(redirectUrl), clientId, clientSecret);

        try {
            JSONResource response = r.json(ACCESS_TOKEN_URL, form(url));
            access_token = response.get("access_token").toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(access_token);
    }

    @Override
    public boolean hasAccessToken() {
        return StringUtils.isNotBlank(accessToken);
    }

    @Override
    public String provideAccessToken() {
        return accessToken;
    }
}
