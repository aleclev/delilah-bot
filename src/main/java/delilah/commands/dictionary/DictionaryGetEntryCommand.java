package delilah.commands.dictionary;

import delilah.commands.AbstractSlashCommand;
import delilah.models.user.User;
import delilah.repositories.DictionaryRepository;
import delilah.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DictionaryGetEntryCommand extends AbstractSlashCommand {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    public DictionaryGetEntryCommand() {
        super("dictionary-find-entry", "Find an entry in the specified dictionary.");

        OptionData word = new OptionData(OptionType.STRING, "word", "The word to search.", true, true);
        OptionData dictionaryId = new OptionData(OptionType.STRING, "dictionary_id", "Dictionary to look into. (Leave blank for personal dictionary)", false);

        addOptions(word, dictionaryId);
    }

    @Override
    protected void execute(SlashCommandInteractionEvent commandEvent) {
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

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        String userInput = event.getFocusedOption().getValue();
        String focusedField = event.getFocusedOption().getName();

        String wordField = null;
        String dictionaryIdField = null;

        try {
            wordField = event.getOption("word").getAsString();
        } catch (Exception e) {}

        try {
            dictionaryIdField = event.getOption("dictionary_id").getAsString();
        } catch (Exception ignored) { }

        if (focusedField.equals("word")) {
            if (dictionaryIdField == null) {
                User user = userRepository.findById(event.getUser().getId()).get();

                event.replyChoices(user.getRootDictionary().getEntries()
                        .keySet().stream()
                        .filter(k -> k.contains(userInput))
                        .map(k -> new Command.Choice(k, k))
                        .collect(Collectors.toList())).queue();
            }
        }
    }
}
