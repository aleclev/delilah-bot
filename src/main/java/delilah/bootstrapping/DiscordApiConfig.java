package delilah.bootstrapping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class DiscordApiConfig {

    @Bean("discordApiRestTemplate")
    RestTemplate discordRestTemplate(@Value("${discord.api.baseUrl}") String discordApiBaseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(discordApiBaseUrl));
        return restTemplate;
    }
}
