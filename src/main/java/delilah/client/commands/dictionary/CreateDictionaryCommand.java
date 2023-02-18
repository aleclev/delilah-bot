package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashCommand;
import delilah.domain.factories.DictionaryFactory;
import delilah.infrastructure.repositories.DictionaryRepository;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
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
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        String dictionaryId = commandEvent.getOption("id").getAsString();
        String userId = commandEvent.getUser().getId();
        //User user = userRepository.findById(userId).get();
        //Dictionary dictionary = dictionaryFactory.createDefaultDictionary(dictionaryId, user);

        //dictionaryRepository.save(dictionary);

        commandEvent.reply("Dictionary created!").queue();
    }
}
