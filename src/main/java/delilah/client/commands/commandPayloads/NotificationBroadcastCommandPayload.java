package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;

public class NotificationBroadcastCommandPayload {

    @Argument(description = "Message that will be sent to users")
    public String message;

    @Argument(description = "The tag you want to ping")
    public String tag1;

    @Argument(description = "Optionally choose a second tag", optional = true)
    public String tag2;

    @Argument(description = "Optionally choose a third tag", optional = true)
    public String tag3;
}
