package delilah.commands.dictionary;

import delilah.commands.AbstractSlashCommand;
import delilah.models.dictionnary.Dictionary;
import delilah.models.user.User;
import delilah.repositories.DictionaryRepository;
import delilah.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddToDictionaryCommand extends AbstractSlashCommand {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public AddToDictionaryCommand() {
        super("dictionary-add-entry", "Add an entry to the specified dictionary.");

        OptionData word = new OptionData(OptionType.STRING, "word", "Word you want to add.", true);
        OptionData definition = new OptionData(OptionType.STRING, "definition", "Definition to the word.", true);
        OptionData dictionaryId = new OptionData(OptionType.STRING, "dictionary_id", "Dictionary to use. (Leave blank to use personal dictionary)", false);

        addOptions(word, definition, dictionaryId);
    }

    @Override
    protected void execute(SlashCommandInteractionEvent commandEvent) {

        String dictionaryId = null;

        try {
            dictionaryId = commandEvent.getOption("dictionary_id").getAsString();
        } catch (Exception ignored) {
        }

        String word = commandEvent.getOption("word").getAsString();
        String definition = commandEvent.getOption("definition").getAsString();

        User user = userRepository.findById(commandEvent.getUser().getId()).get();

        if (dictionaryId != null) {
            Dictionary dictionary = user.getDictionaryById(dictionaryId);
            dictionary.getEntries().put(word, definition);
            dictionaryRepository.save(dictionary);
        }
        else {
            Dictionary dictionary = user.getRootDictionary();
            dictionary.getEntries().put(word, definition);

            dictionaryRepository.save(dictionary);
        }

        commandEvent.reply("Added entry!").queue();
    }
}
