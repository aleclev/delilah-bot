package delilah.interf4ce.commands.dictionary;

import delilah.interf4ce.commands.AbstractSlashCommand;
import delilah.interf4ce.commands.commandPayloads.AddToDictionnaryPayload;
import delilah.interf4ce.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.DictionaryRepository;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = AddToDictionnaryPayload.class)
public class AddToDictionaryCommand extends AbstractSlashCommand {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public AddToDictionaryCommand() {
        super("dictionary-add-entry", "Add an entry to the specified dictionary.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        AddToDictionnaryPayload payload1 = (AddToDictionnaryPayload)payload;

        String dictionaryId = payload1.dictionaryId;
        String word = payload1.word;
        String definition = payload1.definition;

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
