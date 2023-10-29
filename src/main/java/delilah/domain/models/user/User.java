package delilah.domain.models.user;


import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.permission.Role;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Document()
public class User {

    @Id
    String discordId;

    private String userId;

    private ArrayList<Dictionary> dictionaries;

    private NotificationProfile notificationProfile;

    private Dictionary rootDictionary;

    private PermissionProfile permissionProfile;

    public User(String discordId, String userId, Dictionary rootDictionary, NotificationProfile notificationProfile, PermissionProfile permissionProfile) {
        this.discordId = discordId;
        this.userId = userId;
        this.rootDictionary = rootDictionary;
        this.notificationProfile = notificationProfile;
        this.permissionProfile = permissionProfile;
    }

    public List<NotificationSubscription> getMatchingSubscriptions(List<NotificationSubscription> subscriptions) {
        return notificationProfile.getMatchingSubscriptions(subscriptions);
    }

    public boolean blocksNotificationsFrom(User otherUser) {
        return notificationProfile.blocksUserNotifications(otherUser);
    }

    public boolean hasRole(Role role) {
        if (Objects.nonNull(permissionProfile)) {
            return permissionProfile.hasRole(role);
        }
        return false;
    }

    public void setRootDictionary(Dictionary dictionary) {
        rootDictionary = dictionary;
    }

    public boolean addNotificationSubscription(NotificationSubscription subscription) {
        return notificationProfile.addSubscription(subscription);
    }

    public boolean isAllowedToBroadcast(Duration minimumDelay, Clock clock) {
        return !notificationProfile.isOnBroadcastCooldown(minimumDelay, clock);
    }

    public void logNotificationActivity(Clock clock) {
        notificationProfile.logNotificationACtivity(clock);
    }
    private User() {}


    public boolean removeNotificationSubscription(NotificationSubscription subscription) {
        return notificationProfile.removeSubscription(subscription);
    }

    public boolean muteUser(User userToBlock) {
        return notificationProfile.blockUser(userToBlock.getDiscordId());
    }

    public boolean unmuteUser(User userToUnblock) {
        return notificationProfile.unblockUser(userToUnblock.getDiscordId());
    }
}
