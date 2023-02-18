package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.domain.models.user.User;

public class BlockUserNotificationsCommandPayload {

    @Argument(name = "user_to_block", description = "Block notifications from this user.")
    public User userToBlock;
}
