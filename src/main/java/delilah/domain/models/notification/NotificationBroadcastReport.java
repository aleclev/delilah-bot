package delilah.domain.models.notification;

import delilah.domain.models.user.User;

import java.util.List;

public class NotificationBroadcastReport {

    private List<NotificationSubscription> mentions;
    private List<User> usersFound;
    private List<User> usersBlocking;
    private List<User> usersOnline;
    private List<User> usersPinged;

    public NotificationBroadcastReport(List<NotificationSubscription> mentions, List<User> usersFound, List<User> usersBlocking, List<User> usersOnline, List<User> usersPinged) {
        this.mentions = mentions;
        this.usersFound = usersFound;
        this.usersBlocking = usersBlocking;
        this.usersOnline = usersOnline;
        this.usersPinged = usersPinged;
    }

    public boolean pingedSomeone() {
        return !usersFound.isEmpty();
    }

    public Double onlineRatio() {
        Double allUsers = (double) usersFound.size();
        Double onlineUsers = (double) usersOnline.size();

        return onlineUsers / allUsers;
    }

    public Integer onlineCount() {
        return usersOnline.size();
    }

    public Integer allUsersCount() {
        return usersFound.size();
    }

    @Override
    public String toString() {

        if (pingedSomeone()) {
            return String.format("Messaged %s online users from %s users subscribed to provided tags.", onlineCount(), allUsersCount());
        } else {
            return "No online users found for the provided tags.";
        }
    }
}
