package delilah.commands.misc;

import delilah.commands.AbstractSlashCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.List;

@Component
public class PingCommand extends AbstractSlashCommand {

    @Autowired
    private Random random;

    public PingCommand() {
        super("ping", "Used to check if the bot is active.");
    }

    @Override
    protected void execute(SlashCommandEvent commandEvent) {
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
