package delilah.configuration;

import delilah.discord.EventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class JdaConfig {

    @Autowired
    EventListener listener;

    @Bean
    public JDA jda(@Value("${delilah.discord.token}") String discordToken) throws LoginException {
        return JDABuilder.createDefault(discordToken)
                .addEventListeners(listener)
                .build();
    }
}
