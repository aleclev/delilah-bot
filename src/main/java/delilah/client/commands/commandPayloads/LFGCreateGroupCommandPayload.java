package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;

public class LFGCreateGroupCommandPayload {

    @Argument( description = "The activity for the group.")
    public String activity;

    @Argument( description = "Short description for your event.")
    public String description;

    @Argument(name = "max_size", description = "How many users, including you, can join.")
    public Integer maxSize;
}
