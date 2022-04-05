package discordEventPayloads;

import discordObjects.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageEventPayload {
    private Message message;

    public MessageEventPayload(MessageReceivedEvent evt) {
        message = new Message(evt.getMessage());
    }
}
