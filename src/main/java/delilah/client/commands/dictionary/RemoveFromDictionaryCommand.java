package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.DictionaryGetEntryCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.DictionaryService;
import delilah.services.autocomplete.DictionaryFindEntryAutoCompleteService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = DictionaryGetEntryCommandPayload.class)
public class RemoveFromDictionaryCommand extends AbstractSlashCommand {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    DictionaryFindEntryAutoCompleteService dictionaryFindEntryAutoCompleteService;

    public RemoveFromDictionaryCommand() {
        super("dictionary-remove-entry", "Remove an entry from your personal dictionary.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        DictionaryGetEntryCommandPayload p = (DictionaryGetEntryCommandPayload) payload;
        String discordId = commandEvent.getUser().getId();
        String word = p.word;

        if (dictionaryService.removeDictionaryEntryForUser(discordId, word) == null) commandEvent.reply("Error! Word not found.").queue();
        else commandEvent.reply("Word deleted.").queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        event.replyChoices(dictionaryFindEntryAutoCompleteService.getSuggestions(event)).queue();
    }
}
