package net.bilberry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthKeysStorageConfig {

    @Bean @Singleton
    public AuthStorageConfig prepareAuthKeysStorage() {
        // todo: read database
        return new AuthStorageConfig();
    }

    private class AuthStorageConfig {
        private Map<String, String> keyStorage = new HashMap<>();

        public boolean hasKey(String key) {
            return keyStorage.containsKey(key);
        }

        public void addKey(String userId, String key) {
            keyStorage.put(userId, key);
        }

        public String getKey(String userId) {
            return keyStorage.get(userId);
        }
    }
}
