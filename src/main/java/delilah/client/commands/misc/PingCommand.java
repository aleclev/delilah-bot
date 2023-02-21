package delilah.client.commands.misc;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.AbstractSlashSingleCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.List;

@Component
public class PingCommand extends AbstractSlashSingleCommand {

    @Autowired
    private Random random;

    public PingCommand() {
        super("ping", "Used to check if the bot is active.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        List<String> responses = Arrays.asList(
                "Hello!",
                "Hi!",
                "What's up?",
                "Pong!",
                "Everything ok?",
                "Did you think I was offline?",
                "Yup, I'm here!"
        );

        commandEvent.reply(responses.get(random.nextInt(responses.size()))).queue();
    }
}
