package delilah.client.interactions.slashCommands.registration;

import delilah.client.interactions.slashCommands.AbstractSlashParentCommand;
import delilah.client.interactions.slashCommands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;

@Component
public class RegisterParentCommand extends AbstractSlashParentCommand {
    public RegisterParentCommand(SlashCommandPayloadExtractor payloadExtractor,
                                 RegisterDiscordCommand registerDiscordCommand) {
        super("register", "Parent command to register.", payloadExtractor);

        this.registerSubcommand(registerDiscordCommand);
    }
}
