package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.commandPayloads.NotificationAddSubscriptionCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.NotificationSubscriptionService;
import delilah.services.autocomplete.AddActivityAutocompleteService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = NotificationAddSubscriptionCommandPayload.class)
public class NotificationAddSubscriptionCommand extends AbstractSlashSubcommand {

    private final NotificationSubscriptionService notificationSubscriptionService;
    private final AddActivityAutocompleteService addActivityAutocompleteService;

    public NotificationAddSubscriptionCommand(NotificationSubscriptionService notificationSubscriptionService, AddActivityAutocompleteService addActivityAutocompleteService) {
        super("add", "Add a tag to your subscription list.");
        this.notificationSubscriptionService = notificationSubscriptionService;
        this.addActivityAutocompleteService = addActivityAutocompleteService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (NotificationAddSubscriptionCommandPayload)payload;

        if (notificationSubscriptionService.addTagToUserSubscriptions(commandEvent.getUser().getId(), p.tag))
            commandEvent.reply("Tag added to subscriptions!").queue();
        else
            commandEvent.reply("Tag already add to subscriptions.").queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {
        event.replyChoices(addActivityAutocompleteService.getSuggestions(event)).queue();
    }
}
