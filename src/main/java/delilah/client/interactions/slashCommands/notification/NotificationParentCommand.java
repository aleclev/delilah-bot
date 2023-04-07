package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.AbstractSlashParentCommand;
import delilah.client.interactions.slashCommands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;

@Component
public class NotificationParentCommand extends AbstractSlashParentCommand {
    public NotificationParentCommand(SlashCommandPayloadExtractor payloadExtractor,
                                     NotificationAddSubscriptionCommand addCommand,
                                     NotificationBlockUserCommand blockUserCommand,
                                     NotificationBroadcastCommand broadcastCommand,
                                     NotificationRemoveSubscriptionCommand removeCommand,
                                     NotificationUnblockUserCommand unblockUserCommand,
                                     NotificationListCommand listCommand) {
        super("notif", "Parent to the notification module.", payloadExtractor);

        this.registerSubcommand(blockUserCommand);
        this.registerSubcommand(broadcastCommand);
        this.registerSubcommand(removeCommand);
        this.registerSubcommand(unblockUserCommand);
        this.registerSubcommand(addCommand);
        this.registerSubcommand(listCommand);
    }
}
