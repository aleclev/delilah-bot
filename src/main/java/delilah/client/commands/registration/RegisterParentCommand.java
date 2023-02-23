package delilah.client.commands.registration;

import delilah.client.commands.AbstractSlashParentCommand;
import delilah.client.commands.payloadProcessing.SlashCommandPayloadExtractor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RegisterParentCommand extends AbstractSlashParentCommand {
    public RegisterParentCommand(SlashCommandPayloadExtractor payloadExtractor,
                                 RegisterDiscordCommand registerDiscordCommand) {
        super("register", "Parent command to register.", payloadExtractor);

        this.registerSubcommand(registerDiscordCommand);
    }
}
