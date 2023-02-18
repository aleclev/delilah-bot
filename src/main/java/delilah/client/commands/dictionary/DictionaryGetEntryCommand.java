package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.DictionaryGetEntryCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.dictionnary.DictionaryEntry;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@ConsumesPayload(type = DictionaryGetEntryCommandPayload.class)
public class DictionaryGetEntryCommand extends AbstractSlashCommand {

    @Autowired
    DictionaryService dictionaryService;

    public DictionaryGetEntryCommand() {
        super("dictionary-find-entry", "Find an entry in the specified dictionary.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        DictionaryGetEntryCommandPayload p = (DictionaryGetEntryCommandPayload)payload;

        String word = p.word;
        String discordId = commandEvent.getUser().getId();
        DictionaryEntry entry = dictionaryService.getDictionaryEntryForUser(discordId, word);

        if (entry == null)
            commandEvent.reply("Word not found.").queue();
        else
            commandEvent.reply(String.format("%s", entry)).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        String userInput = event.getFocusedOption().getValue();

        String discordId = event.getUser().getId();

        event.replyChoices(dictionaryService.getEntriesLikeInputForUser(discordId, userInput).stream()
                .map(k -> new Command.Choice(k.getWord().toString(), k.getWord().toString()))
                .collect(Collectors.toList())).queue();
    }
}
