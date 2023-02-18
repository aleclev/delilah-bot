package delilah.domain.factories;

import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationActivityLog;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserFactory {

    public User createUser(String discordId, Dictionary dictionary) {
        String userId = UUID.randomUUID().toString();

        return new User(discordId, userId, new ArrayList<>(), dictionary, new NotificationProfile(new ArrayList<>(), new NotificationActivityLog(), new ArrayList<>()));
    }
}
