package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public class PingCommand extends Command {

    private Random random;

    public PingCommand(Random random) {
        this.random = random;
        this.name = "ping";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        List<String> responses = Arrays.asList(
                "Hello!",
                "Hi!",
                "What's up?",
                "Pong!",
                "Everything ok?",
                "Did you think I was offline?",
                "Yup, I'm here!"
        );

        commandEvent.reply(responses.get(random.nextInt(responses.size())));
    }
}
