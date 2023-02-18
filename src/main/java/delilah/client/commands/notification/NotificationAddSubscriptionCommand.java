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
public class NotificationAddSubscriptionCommand extends AbstractSlashCommand {

    @Autowired
    NotificationSubscriptionService notificationSubscriptionService;

    public NotificationAddSubscriptionCommand() {
        super("notification-add", "Add a tag to your subscription list.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (NotificationAddSubscriptionCommandPayload)payload;

        if (notificationSubscriptionService.addTagToUserSubscriptions(commandEvent.getUser().getId(), p.tag))
            commandEvent.reply("Tag added to subscriptions!").queue();
        else
            commandEvent.reply("Tag already add to subscriptions.").queue();
    }
}
