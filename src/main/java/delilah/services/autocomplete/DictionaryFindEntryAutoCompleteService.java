package delilah.services.autocomplete;

import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DictionaryFindEntryAutoCompleteService implements AutoCompleteService {

    @Autowired
    DictionaryService dictionaryService;

    @Override
    public List<Command.Choice> getSuggestions(CommandAutoCompleteInteraction event) {
        String userInput = event.getFocusedOption().getValue();
        String discordId = event.getUser().getId();

        return dictionaryService.getEntriesLikeInputForUser(discordId, userInput).stream()
                .map(k -> new Command.Choice(k.getWord().toString(), k.getWord().toString()))
                .collect(Collectors.toList());
    }
}
