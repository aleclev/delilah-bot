package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.commandPayloads.NotificationAddSubscriptionCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.NotificationSubscriptionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ConsumesPayload(type = NotificationAddSubscriptionCommandPayload.class)
public class NotificationRemoveSubscriptionCommand extends AbstractSlashSubcommand {

    @Autowired
    NotificationSubscriptionService notificationSubscriptionService;

    public NotificationRemoveSubscriptionCommand() {
        super("remove", "Remove a tag from your notifications.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (NotificationAddSubscriptionCommandPayload)payload;

        if (notificationSubscriptionService.removeTagFromUserSubscriptions(commandEvent.getUser().getId(), p.tag))
            commandEvent.reply("Tag added to subscriptions!").queue();
        else
            commandEvent.reply("Tag already add to subscriptions.").queue();

    }
}
