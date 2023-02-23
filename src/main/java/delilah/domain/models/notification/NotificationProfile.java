package delilah.domain.models.notification;

import delilah.domain.models.user.User;
import lombok.Getter;

import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NotificationProfile {

    private List<NotificationSubscription> subscriptions;
    private NotificationActivityLog notificationActivityLog;
    private List<String> blockedUsersDiscordIds;

    public NotificationProfile(List<NotificationSubscription> subscriptions, NotificationActivityLog notificationActivityLog, List<String> blockedUsersDiscordIds) {
        this.subscriptions = subscriptions;
        this.notificationActivityLog = notificationActivityLog;
        this.blockedUsersDiscordIds = blockedUsersDiscordIds;
    }

    public boolean addSubscription(NotificationSubscription subscription) {
        if (hasSubscription(subscription)) return true;
        else return subscriptions.add(subscription);
    }

    public boolean blockUser(String discordId) {
        if (blockedUsersDiscordIds.contains(discordId)) return false;
        else return blockedUsersDiscordIds.add(discordId);
    }

    public boolean unblockUser(String discordId) {
        if (!blockedUsersDiscordIds.contains(discordId)) return false;
        else return blockedUsersDiscordIds.remove(discordId);
    }
    public boolean hasSubscription(NotificationSubscription subscription) {
        return subscriptions.stream().filter(s -> s.equals(subscription)).count() > 0;
    }

    public boolean isOnBroadcastCooldown(Duration minimalDelay, Clock clock) {

        return notificationActivityLog.timeElapsedSinceLastActivity(clock).compareTo(minimalDelay) > 0;
    }

    public List<NotificationSubscription> getMatchingSubscriptions(List<NotificationSubscription> subscriptions) {
        return this.subscriptions.stream().filter(subscriptions::contains).collect(Collectors.toList());
    }

    public boolean blocksUserNotifications(User otherUser) {
        return blockedUsersDiscordIds.contains(otherUser.getDiscordId());
    }

    public boolean removeSubscription(NotificationSubscription subscription) {
        return subscriptions.remove(subscription);
    }

    public void logNotificationACtivity(Clock clock) {
        notificationActivityLog.logActivity(clock);
    }
}
