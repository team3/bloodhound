package net.bilberry.candidate.service.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.font.TextRecord;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.util.Optional;

import static us.monoid.web.Resty.enc;
import static us.monoid.web.Resty.form;

@Service
public class StackoverflowAuthService implements OAuthService {
    private static final String AUTH_URL = "https://stackexchange.com/oauth?response_type=code&client_id=%s&redirect_uri=%s&scope=%s&state=%s";
    private static final String ACCESS_TOKEN_URL = "https://stackexchange.com/oauth/access_token";
    private static final String ACCESS_TOKEN_URI_PARAMS = "code=%s&redirect_uri=%s&client_id=%s&client_secret=%s";

    @Value("${stackoverflow.auth.redirect_url}")
    private String redirectUrl;

    @Value("${stackoverflow.auth.client.id}")
    private String clientId;

    @Value("${stackoverflow.auth.client.secret}")
    private String clientSecret;

    @Value("${stackoverflow.auth.scope}")
    private String scope;

    // TODO: temporary solution - storing access token in properties file.
    @Value("${stackoverflow.auth.access.token}")
    private String accessToken;

    @Override
    public boolean hasAccessToken() {
        return StringUtils.isNotBlank(accessToken);
    }

    @Override
    public String provideAccessToken() {
        return accessToken;
    }

    @Override
    public String prepareAuthUrl() {
        return String.format(AUTH_URL, clientId, enc(redirectUrl), scope, String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public Optional<String> requestAccessToken(final String code) {
        assert StringUtils.isNotBlank(code);

        String access_token = null;
        Resty r = new Resty().identifyAsMozilla();
        final String url = String.format(ACCESS_TOKEN_URI_PARAMS, code, enc(redirectUrl), clientId, clientSecret);

        try {
            String response = r.text(ACCESS_TOKEN_URL, form(url)).toString();

            if (response.contains("access_token=")) {
                access_token = StringUtils.replace(response, "access_token=", StringUtils.EMPTY);
            }

            System.out.println(access_token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(access_token);
    }
}
