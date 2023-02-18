package delilah.domain.models.notification;

import java.util.List;

public class NotificationBroadcastReport {

    private List<NotificationSubscription> mentions;
    private List<String> usersFound;
    private List<String> usersBlocking;
    private List<String> usersOnline;
    private List<String> usersPinged;
}
