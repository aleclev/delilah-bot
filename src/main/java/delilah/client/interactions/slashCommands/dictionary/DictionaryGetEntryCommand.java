package delilah.client.interactions.slashCommands.dictionary;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.commandPayloads.DictionaryGetEntryCommandPayload;
import delilah.domain.models.dictionnary.DictionaryEntry;
import delilah.services.DictionaryService;
import delilah.services.autocomplete.DictionaryFindEntryAutoCompleteService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = DictionaryGetEntryCommandPayload.class)
public class DictionaryGetEntryCommand extends AbstractSlashSubcommand {

    @Autowired
    DictionaryFindEntryAutoCompleteService dictionaryFindEntryAutoCompleteService;

    @Autowired
    DictionaryService dictionaryService;

    public DictionaryGetEntryCommand() {
        super("get", "Find an entry in the specified dictionary.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        DictionaryGetEntryCommandPayload p = (DictionaryGetEntryCommandPayload)payload;

        String word = p.word;
        String discordId = commandEvent.getUser().getId();
        DictionaryEntry entry = dictionaryService.getDictionaryEntryForUser(discordId, word);

        commandEvent.reply(entry.toString()).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        event.replyChoices(dictionaryFindEntryAutoCompleteService.getSuggestions(event)).queue();
    }
}
