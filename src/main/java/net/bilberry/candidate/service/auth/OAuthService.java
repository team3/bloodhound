package net.bilberry.candidate.service.auth;

import java.util.Optional;

public interface OAuthService {
    boolean hasAccessToken();
    String provideAccessToken();
    String prepareAuthUrl();
    Optional<String> requestAccessToken(final String code);
}
