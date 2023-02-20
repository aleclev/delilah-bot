package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.domain.models.user.User;

public class BlockUserNotificationsCommandPayload {

    @Argument(description = "Block notifications from this user.")
    public User user;
}
