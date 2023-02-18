package delilah.domain.models.user;


import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.notification.NotificationSubscription;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

@Getter
public class User {

    @Id
    String discordId;

    String userId;

    List<Dictionary> dictionaries;

    NotificationProfile notificationProfile;

    Dictionary rootDictionary;

    public User(String discordId, String userId, List<Dictionary> dictionaries, Dictionary rootDictionary, NotificationProfile notificationProfile) {
        this.discordId = discordId;
        this.userId = userId;
        this.dictionaries = dictionaries;
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

    public void addDictionary(Dictionary dictionary) {
        if (dictionaries.size() >= 10) return;

        dictionaries.add(dictionary);
    }

    public boolean isAllowedToBroadcast(Duration minimumDelay, Clock clock) {
        return !notificationProfile.isOnBroadcastCooldown(minimumDelay, clock);
    }

    public void logNotificationActivity(Clock clock) {
        notificationProfile.logNotificationACtivity(clock);
    }
    private User() {}

    public Dictionary getDictionaryById(String id) {
        return dictionaries.stream().filter(d -> d.getDictionaryId().equals(id)).findFirst().get();
    }

    public boolean removeNotificationSubscription(NotificationSubscription subscription) {
        return notificationProfile.removeSubscription(subscription);
    }
}
