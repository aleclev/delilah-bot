package models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
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
