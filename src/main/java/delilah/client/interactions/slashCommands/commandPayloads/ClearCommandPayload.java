package delilah.client.interactions.slashCommands.commandPayloads;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.Argument;

public class ClearCommandPayload {

    @Argument(description = "The number of messages to delete")
    public Integer amount;
}
