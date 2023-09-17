package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.commandPayloads.NotificationAddSubscriptionCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.NotificationSubscriptionService;
import delilah.services.autocomplete.RemoveActivityAutocompleteService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.stereotype.Component;


@Component
@ConsumesPayload(type = NotificationAddSubscriptionCommandPayload.class)
public class NotificationRemoveSubscriptionCommand extends AbstractSlashSubcommand {

    private final NotificationSubscriptionService notificationSubscriptionService;
    private final RemoveActivityAutocompleteService removeActivityAutocompleteService;


    public NotificationRemoveSubscriptionCommand(NotificationSubscriptionService notificationSubscriptionService, RemoveActivityAutocompleteService removeActivityAutocompleteService) {
        super("remove", "Remove a tag from your notifications.");
        this.notificationSubscriptionService = notificationSubscriptionService;
        this.removeActivityAutocompleteService = removeActivityAutocompleteService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (NotificationAddSubscriptionCommandPayload)payload;

        if (notificationSubscriptionService.removeTagFromUserSubscriptions(commandEvent.getUser().getId(), p.tag))
            commandEvent.reply("Tag added to subscriptions!").queue();
        else
            commandEvent.reply("Tag already add to subscriptions.").queue();

    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {
        event.replyChoices(removeActivityAutocompleteService.getSuggestions(event)).queue();
    }
}
