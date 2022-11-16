package delilah.commands.registration;

import delilah.commands.AbstractSlashCommand;
import delilah.factories.DictionaryFactory;
import delilah.factories.UserFactory;
import delilah.models.dictionnary.Dictionary;
import delilah.models.user.User;
import delilah.repositories.DictionaryRepository;
import delilah.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterDiscordCommand extends AbstractSlashCommand {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    UserFactory userFactory;

    @Autowired
    DictionaryFactory dictionaryFactory;

    public RegisterDiscordCommand() {
        super("register-discord", "Register your discord profile with Delilah.");
    }

    @Override
    protected void execute(SlashCommandInteractionEvent commandEvent) {

        User user = userFactory.createUser(commandEvent.getUser().getId());
        Dictionary dictionary = dictionaryFactory.createDefaultRootDictionary();
        userRepository.save(user);
        dictionary.setOwner(user);
        dictionaryRepository.save(dictionary);
        user.setRootDictionary(dictionary);
        userRepository.save(user);

        commandEvent.reply("User saved.").queue();
    }
}
