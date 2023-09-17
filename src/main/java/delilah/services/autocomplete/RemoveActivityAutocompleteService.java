package delilah.services.autocomplete;

import delilah.domain.models.lookingForGroup.Activity;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.ActivityRepository;
import delilah.infrastructure.repositories.UserRepository;
import delilah.services.UserService;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RemoveActivityAutocompleteService implements AutoCompleteService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public RemoveActivityAutocompleteService(UserRepository userRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Command.Choice> getSuggestions(CommandAutoCompleteInteraction event) {
        User user = userRepository.findByDiscordId(event.getUser().getId());
        List<NotificationSubscription> subscribed = new ArrayList<>();

        if (Objects.nonNull(user)) subscribed = user.getNotificationProfile().getSubscriptions();

        String prompt = event.getFocusedOption().getValue();

        return subscribed.stream()
                .filter(n -> n.getTag().contains(prompt))
                .map(n -> new Command.Choice(n.getTag(), n.getTag()))
                .limit(25).collect(Collectors.toList());
    }
}
