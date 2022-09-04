package delilah.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandDispatcher {
    @Autowired
    List<AbstractSlashCommand> commands;

    public void handle(SlashCommandEvent commandEvent) {
        AbstractSlashCommand command = getCommand(commandEvent.getName());

        if (command == null) return;

        command.execute(commandEvent);
    }

    public AbstractSlashCommand getCommand(String name) {
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst().get();
    }
}
