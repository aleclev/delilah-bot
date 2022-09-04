package delilah.factories;

import delilah.models.user.User;

import java.util.UUID;

public class UserFactory {

    public User createUser(Long discordId) {
        String userId = UUID.randomUUID().toString();

        return new User(discordId, userId);
    }
}
