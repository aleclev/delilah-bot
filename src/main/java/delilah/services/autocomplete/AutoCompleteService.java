package delilah.services.autocomplete;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

import java.util.List;

public interface AutoCompleteService {

    List<Command.Choice> getSuggestions(CommandAutoCompleteInteraction event);
}
