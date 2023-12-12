package delilah.client.interactions.slashCommands.dictionary;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.commandPayloads.AddToDictionnaryPayload;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = AddToDictionnaryPayload.class)
public class AddToDictionaryCommand extends AbstractSlashSubcommand {

    @Autowired
    DictionaryService dictionaryService;

    public AddToDictionaryCommand() {
        super("add", "Add an entry to the specified dictionary.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        AddToDictionnaryPayload payload1 = (AddToDictionnaryPayload)payload;

        String dictionaryId = payload1.dictionaryId;
        String word = payload1.word;
        String definition = payload1.definition;
        String discordId = commandEvent.getUser().getId();

        dictionaryService.addToUserDictionary(discordId, word, definition);

        commandEvent.reply("Entry Added!").queue();
    }
}
