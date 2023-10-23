package delilah.api.rest.dto;

public class DiscordChannelDTO {
    public String name;
    public String id;

    public DiscordChannelDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
