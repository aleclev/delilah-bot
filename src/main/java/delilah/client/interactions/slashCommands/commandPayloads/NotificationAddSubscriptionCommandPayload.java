package delilah.client.interactions.slashCommands.commandPayloads;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.Argument;

public class NotificationAddSubscriptionCommandPayload {

    @Argument(description = "The tag to add to your subscriptions.", autocomplete = true)
    public String tag;
}
