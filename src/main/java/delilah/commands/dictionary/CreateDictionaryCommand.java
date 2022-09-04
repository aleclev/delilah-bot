package delilah.commands.dictionary;

import delilah.commands.AbstractSlashCommand;
import delilah.factories.DictionaryFactory;
import delilah.models.dictionnary.Dictionary;
import delilah.models.user.User;
import delilah.repositories.DictionaryRepository;
import delilah.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateDictionaryCommand extends AbstractSlashCommand {

    @Autowired
    DictionaryFactory dictionaryFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    public CreateDictionaryCommand() {
        super("dictionary-create", "Create a private empty dictionary.");

        OptionData idOption = new OptionData(OptionType.STRING, "id", "A unique id you will use to access this dictionary.", true);

        addOptions(idOption);
    }

    @Override
    protected void execute(SlashCommandEvent commandEvent) {

        String dictionaryId = commandEvent.getOption("id").getAsString();
        String userId = commandEvent.getUser().getId();
        User user = userRepository.findById(userId).get();
        Dictionary dictionary = dictionaryFactory.createDefaultDictionary(dictionaryId, user);

        dictionaryRepository.save(dictionary);

        commandEvent.reply("Dictionary created!").queue();
    }
}
