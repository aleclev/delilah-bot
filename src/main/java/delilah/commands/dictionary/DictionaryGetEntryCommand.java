package delilah.commands.dictionary;

import delilah.commands.AbstractSlashCommand;
import delilah.models.user.User;
import delilah.repositories.DictionaryRepository;
import delilah.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryGetEntryCommand extends AbstractSlashCommand {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    public DictionaryGetEntryCommand() {
        super("dictionary-find-entry", "Find an entry in the specified dictionary.");

        OptionData word = new OptionData(OptionType.STRING, "word", "The word to search.", true);
        OptionData dictionaryId = new OptionData(OptionType.STRING, "dictionary_id", "Dictionary to look into. (Leave blank for personal dictionary)");

        addOptions(word, dictionaryId);
    }

    @Override
    protected void execute(SlashCommandEvent commandEvent) {
        String dictionaryId = null;

        try {
            dictionaryId = commandEvent.getOption("dictionary_id").getAsString();
        } catch (Exception ignored) { }

        String word = commandEvent.getOption("word").getAsString();

        if (dictionaryId != null) {

        }
        else {
            User user = userRepository.findById(commandEvent.getUser().getId()).get();

            String definition = user.getRootDictionary().getEntries().get(word);

            commandEvent.reply(String.format("**%s**:\n%s", word, definition)).queue();
        }
    }
}
