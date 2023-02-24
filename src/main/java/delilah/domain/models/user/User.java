package delilah.domain.models.user;


import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.notification.NotificationSubscription;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Document()
public class User {

    @Id
    String discordId;

    String userId;

    ArrayList<Dictionary> dictionaries;

    NotificationProfile notificationProfile;

    Dictionary rootDictionary;

    public User(String discordId, String userId, Dictionary rootDictionary, NotificationProfile notificationProfile) {
        this.discordId = discordId;
        this.userId = userId;
        this.rootDictionary = rootDictionary;
        this.notificationProfile = notificationProfile;
    }

    public List<NotificationSubscription> getMatchingSubscriptions(List<NotificationSubscription> subscriptions) {
        return notificationProfile.getMatchingSubscriptions(subscriptions);
    }

    public boolean blocksNotificationsFrom(User otherUser) {
        return notificationProfile.blocksUserNotifications(otherUser);
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
