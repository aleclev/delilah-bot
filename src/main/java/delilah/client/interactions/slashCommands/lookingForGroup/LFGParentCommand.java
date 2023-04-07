package delilah.client.interactions.slashCommands.lookingForGroup;

import delilah.client.interactions.slashCommands.AbstractSlashParentCommand;
import delilah.client.interactions.slashCommands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;

@Component
public class LFGParentCommand extends AbstractSlashParentCommand {
    public LFGParentCommand(SlashCommandPayloadExtractor payloadExtractor,
                            LFGCreateGroupCommand createGroupCommand) {
        super("lfg", "Parent command to LFG module.", payloadExtractor);

        this.registerSubcommand(createGroupCommand);
    }
}
