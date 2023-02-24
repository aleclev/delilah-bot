package delilah.services.autocomplete;

import delilah.infrastructure.repositories.ActivityRepository;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityAutocompleteService implements AutoCompleteService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Command.Choice> getSuggestions(CommandAutoCompleteInteraction event) {
        return activityRepository
                .searchByName(event.getFocusedOption().getValue())
                .stream()
                .map(a -> new Command.Choice(a.getLongName(), a.getShortName()))
                .limit(25)
                .collect(Collectors.toList());
    }
}
