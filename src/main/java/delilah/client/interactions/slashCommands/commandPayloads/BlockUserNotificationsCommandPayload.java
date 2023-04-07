package delilah.client.interactions.slashCommands.commandPayloads;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.Argument;
import delilah.domain.models.user.User;

public class BlockUserNotificationsCommandPayload {

    @Argument(description = "Block notifications from this user.")
    public User user;
}
