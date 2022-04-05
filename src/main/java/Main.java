import discordEventHandlers.jda.MessageEventHandlerJDA;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
                .build();

        jda.awaitReady();

        jda.addEventListener(new MessageEventHandlerJDA());
    }
}
