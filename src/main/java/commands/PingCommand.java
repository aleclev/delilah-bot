package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {

    public PingCommand() {
        this.name = "ping";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        commandEvent.reply("hello !");
    }
}
