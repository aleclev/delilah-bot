package delilah.services.discord;

import delilah.api.rest.dto.DiscordTokenDto;
import delilah.api.rest.dto.DiscordTokenRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class DiscordAuthService {

    private final String redirectUri;
    private final String clientSecret;
    private final String clientId;
    private final String grantType = "authorization_code";

    private String tokenPath;
    private final RestTemplate restTemplate;

    public DiscordAuthService(@Value("${delilah.web.login.redirect_url}") String redirectUri,
                              @Value("${delilah.discord.client_secret}") String clientSecret,
                              @Value("${delilah.discord.client_id}") String clientId,
                              @Value("${discord.api.auth.token.path}") String tokenPath,
                              @Qualifier("discordApiRestTemplate") RestTemplate restTemplate) {

        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.tokenPath = tokenPath;
        this.restTemplate = restTemplate;
    }

    public DiscordTokenDto getAccessTokenFromCode(String code) {

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_secret", clientSecret);
        params.add("client_id", clientId);
        params.add("grant_type", grantType);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Object> entity = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(tokenPath, entity, DiscordTokenDto.class, params);
    }
}
