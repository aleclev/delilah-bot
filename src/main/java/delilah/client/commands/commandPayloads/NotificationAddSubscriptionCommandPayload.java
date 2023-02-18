package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;

public class NotificationAddSubscriptionCommandPayload {

    @Argument(description = "The tag to add to your subscriptions.")
    public String tag;
}
