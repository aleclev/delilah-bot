package delilah.client.commands.registration;

import delilah.client.commands.AbstractSlashSubcommand;
import delilah.services.UserService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterDiscordCommand extends AbstractSlashSubcommand {

    @Autowired
    UserService userService;

    public RegisterDiscordCommand() {
        super("discord", "Register your discord profile with Delilah.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        String id = commandEvent.getUser().getId();

        if (userService.createUserIfNotExists(id))
            commandEvent.reply("User successfully created.").queue();
        else
            commandEvent.reply("Already registered.").queue();
    }
}
