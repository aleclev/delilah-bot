package delilah.client.commands.notification;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.NotificationAddSubscriptionCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.NotificationSubscriptionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ConsumesPayload(type = NotificationAddSubscriptionCommandPayload.class)
public class NotificationRemoveSubscriptionCommand extends AbstractSlashCommand {

    @Autowired
    NotificationSubscriptionService notificationSubscriptionService;

    public NotificationRemoveSubscriptionCommand() {
        super("notification-remove-subscription", "Remove a tag from your notifications.");
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
