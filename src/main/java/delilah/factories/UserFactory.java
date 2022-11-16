package delilah.factories;

import delilah.models.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserFactory {

    public User createUser(String discordId) {
        String userId = UUID.randomUUID().toString();

        return new User(discordId, userId, new ArrayList<>(), null);
    }
}
