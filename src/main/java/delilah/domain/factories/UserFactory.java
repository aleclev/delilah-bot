package delilah.domain.factories;

import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.user.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFactory {

    public User createUser(String discordId, Dictionary dictionary, PermissionProfile permissionProfile, NotificationProfile notificationProfile) {

        return new User(discordId, dictionary, notificationProfile, permissionProfile);
    }
}
