package delilah.client.interactions.slashCommands.commandPayloads;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.Argument;

public class GroupEventCreateCommandPayload {

    @Argument( description = "The activity for the group.", autocomplete = true)
    public String activity;

    @Argument( description = "Short description for your event.")
    public String description;

    @Argument(name = "max_size", description = "How many users, including you, can join.")
    public Integer maxSize;

    @Argument(name="start_time", description = "UTC only. Use this format: yyyy-MM-ddTHH:mm:ssZ (example: 2019-09-02T12:38:16Z)", optional = true)
    public String startTime;
}
