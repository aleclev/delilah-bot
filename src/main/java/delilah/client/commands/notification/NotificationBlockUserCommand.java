package delilah.client.commands.notification;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.AbstractSlashSingleCommand;
import delilah.client.commands.AbstractSlashSubcommand;
import delilah.client.commands.commandPayloads.BlockUserNotificationsCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
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
