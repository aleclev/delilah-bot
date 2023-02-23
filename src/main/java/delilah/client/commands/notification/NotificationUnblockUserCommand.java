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

import java.util.Objects;

@Component
@ConsumesPayload(type = BlockUserNotificationsCommandPayload.class)
public class NotificationUnblockUserCommand extends AbstractSlashSubcommand {

    @Autowired
    private NotificationSubscriptionService notificationSubscriptionService;

    public NotificationUnblockUserCommand() {
        super("unblock", "Unblocks notifications from the specified user.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (BlockUserNotificationsCommandPayload)payload;

        if (Objects.isNull(p.user)) {
            commandEvent.reply("Cannot unblock an unregistered user.").queue();
            return;
        }

        if (notificationSubscriptionService.unblockUser(commandEvent.getUser().getId(), p.user))
            commandEvent.reply("User was unblocked!").queue();
        else
            commandEvent.reply("This user was not on your block list.").queue();
    }
}
