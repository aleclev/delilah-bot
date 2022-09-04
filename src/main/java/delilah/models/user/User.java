package delilah.models.user;


public class User {

    Long discordId;
    String userId;

    private User() {}

    public User(Long discordId, String userId) {
        this.discordId = discordId;
        this.userId = userId;
    }

    public Long getDiscordId() {
        return discordId;
    }

    public String getUserId() {
        return userId;
    }
}
