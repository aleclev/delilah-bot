package delilah.client.commands.notification;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.BlockUserNotificationsCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@ConsumesPayload(type = BlockUserNotificationsCommandPayload.class)
public class NotificationBlockUserCommand extends AbstractSlashCommand {

    public NotificationBlockUserCommand() {
        super("notification-block-user", "Block notifications from specified user.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {
        var p = (BlockUserNotificationsCommandPayload)payload;


    }
}
