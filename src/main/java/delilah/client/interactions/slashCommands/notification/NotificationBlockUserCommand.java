package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.commandPayloads.BlockUserNotificationsCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.NotificationSubscriptionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = BlockUserNotificationsCommandPayload.class)
public class NotificationBlockUserCommand extends AbstractSlashSubcommand {

    @Autowired
    private NotificationSubscriptionService notificationSubscriptionService;

    public NotificationBlockUserCommand() {
        super("block", "Block notifications from specified user.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (BlockUserNotificationsCommandPayload)payload;

        if (notificationSubscriptionService.blockUser(commandEvent.getUser().getId(), p.user))
            commandEvent.reply("User blocked!").queue();
        else
            commandEvent.reply("User is already blocked.").queue();

    }
}
