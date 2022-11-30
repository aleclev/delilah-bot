package delilah.interf4ce.commands.registration;

import delilah.interf4ce.commands.AbstractSlashCommand;
import delilah.domain.factories.DictionaryFactory;
import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.DictionaryRepository;
import delilah.infrastructure.repositories.UserRepository;
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
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

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
