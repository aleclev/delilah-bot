package testUtil;

import delilah.domain.models.notification.NotificationActivityLog;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.notification.NotificationSubscription;

import java.util.ArrayList;

public class NotificationProfileBuilder {

    private ArrayList<NotificationSubscription> subscriptions = new ArrayList<>();
    private ArrayList<String> blockedIds = new ArrayList<>();
    private NotificationActivityLog notificationActivityLog = new NotificationActivityLog();

    public NotificationProfile build() {
        return new NotificationProfile(subscriptions, notificationActivityLog, blockedIds);
    }
}
