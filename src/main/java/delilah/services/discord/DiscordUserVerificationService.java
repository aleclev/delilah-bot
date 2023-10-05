package delilah.services.discord;

import delilah.api.rest.dto.DiscordUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class DiscordUserVerificationService {

    private String userMeEndpoint;
    private RestTemplate restTemplate;

    public DiscordUserVerificationService(@Value("${discord.api.user.me}") String userMeEndpoint, @Qualifier("discordApiRestTemplate") RestTemplate restTemplate) {

        this.userMeEndpoint = userMeEndpoint;
        this.restTemplate = restTemplate;
    }

    public DiscordUserDTO verifyUserFromAccessToken(String accessToken) throws HttpClientErrorException {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        ResponseEntity<DiscordUserDTO> response =
                restTemplate.exchange(userMeEndpoint, HttpMethod.GET, new HttpEntity<>(null, headers), DiscordUserDTO.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) return null;

        return response.getBody();
    }
}
