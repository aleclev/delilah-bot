package delilah.client.commands.lookingForGroup;

import delilah.client.commands.AbstractSlashParentCommand;
import delilah.client.commands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;

@Component
public class LFGParentCommand extends AbstractSlashParentCommand {
    public LFGParentCommand(SlashCommandPayloadExtractor payloadExtractor,
                            LFGCreateGroupCommand createGroupCommand) {
        super("lfg", "Parent command to LFG module.", payloadExtractor);

        this.registerSubcommand(createGroupCommand);
    }
}
