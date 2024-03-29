package testUtil;

import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.user.User;

public class UserBuilder {

    private String discordId;
    private String userId;
    private Dictionary rootDictionary;
    private NotificationProfile notificationProfile;
    private PermissionProfile permissionProfile;

    public UserBuilder genericUser() {
        discordId = "1234567890";
        userId = "fafafafafafafa";

        rootDictionary = (new DictionaryBuilder()).build();
        notificationProfile = (new NotificationProfileBuilder()).build();
        permissionProfile = (new PermissionProfileBuilder()).build();

        return this;
    }

    public UserBuilder withSubscription(NotificationSubscription subscription) {

        notificationProfile.addSubscription(subscription);

        return this;
    }
    public User build() {
        return new User(discordId, rootDictionary, notificationProfile, permissionProfile);
    }
}
