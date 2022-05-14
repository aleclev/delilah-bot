import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import commands.PingCommand;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame();
        client.setOwnerId(System.getenv("DISCORD_ID"));
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");
        client.setPrefix(System.getenv("DISCORD_PREFIX"));

        client.addCommands(
                new PingCommand()
        );

        JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
        .addEventListeners(waiter, client.build())
        .build();
    }
}

