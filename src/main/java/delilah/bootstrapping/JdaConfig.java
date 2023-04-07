package delilah.bootstrapping;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class JdaConfig {

    @Bean
    public JDA jda(@Value("${delilah.discord.token}") String discordToken) throws LoginException {
        return JDABuilder.createDefault(discordToken)
                .setMemberCachePolicy(MemberCachePolicy.ALL) // This is necessary to check member online status
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .enableCache(CacheFlag.ONLINE_STATUS)
                .build();
    }
}
