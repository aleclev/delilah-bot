package discordObjects;

public class Message {
    public String getContent() {
        return content;
    }

    public String content;

    public Message(net.dv8tion.jda.api.entities.Message message) {
        this.content = message.getContentRaw();
    }
}
