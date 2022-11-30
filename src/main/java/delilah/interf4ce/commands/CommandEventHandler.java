package delilah.interf4ce.commands;

import delilah.interf4ce.commands.payloadProcessing.SlashCommandPayloadExtractor;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandEventHandler {
    @Autowired
    List<AbstractSlashCommand> commands;

    @Autowired
    private SlashCommandPayloadExtractor payloadExtractor;

    public void onSlashcommandInteractionEvent(SlashCommandInteractionEvent commandEvent) {
        AbstractSlashCommand command = getCommand(commandEvent.getName());

        if (command == null) return;

        new SlashCommandRunner(command, commandEvent, payloadExtractor).run();
    }

    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        getCommand(event.getName()).onCommandAutoCompleteInteraction(event);
    }

    private AbstractSlashCommand getCommand(String name) {
        return commands.stream().filter(command -> command.getName().equals(name)).findFirst().get();
    }
}
