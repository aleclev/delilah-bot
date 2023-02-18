package delilah.client.commands.registration;

import delilah.client.commands.AbstractSlashCommand;
import delilah.domain.factories.DictionaryFactory;
import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.DictionaryRepository;
import delilah.infrastructure.repositories.UserRepository;
import delilah.services.UserService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterDiscordCommand extends AbstractSlashCommand {

    @Autowired
    UserService userService;

    public RegisterDiscordCommand() {
        super("register-discord", "Register your discord profile with Delilah.");
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
