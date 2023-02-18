package delilah.services;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import delilah.domain.exceptions.NotificationCooldownException;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationBroadcastService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JDA jda;

    @Value("${delilah.notification_activity_delay}")
    Integer minimumMinuteDelay;

    public List<User> broadCastToTagsAsUser(List<String> tags, String discordId, Guild guild, Clock clock)
            throws NotificationCooldownException {
        User user = userService.getOrRegisterUserByDiscordId(discordId);

        Duration minimumDelayForNotification = Duration.of(minimumMinuteDelay, ChronoUnit.MINUTES);

        if (user.isAllowedToBroadcast(minimumDelayForNotification, clock))
            throw new NotificationCooldownException(String.format("This action has a %s minutes cooldown. Come back later.", minimumMinuteDelay));

        List<NotificationSubscription> subscriptions = tags.stream().map(NotificationSubscription::new).collect(Collectors.toList());

        List<User> subscribedUsers = userRepository.findBySubscriptions(subscriptions);
        List<User> onlineUsers = getOnlineUsers(subscribedUsers, guild);
        List<User> blockedUsers = getBlockedUsers(onlineUsers, user);

        subscribedUsers.removeAll(blockedUsers);
        subscribedUsers.retainAll(onlineUsers);

        dispatchMessagesToUsers(subscribedUsers);

        user.logNotificationActivity(clock);
        userRepository.save(user);

        return subscribedUsers;
    }

    private List<User> getOnlineUsers(List<User> users, Guild guild) {
        return users.stream().filter(u -> userWithIdIsOnline(u.getDiscordId(), guild)).collect(Collectors.toList());
    }

    private boolean userWithIdIsOnline(String discordId, Guild guild) {
        OnlineStatus status = guild.getMemberById(discordId).getOnlineStatus(); //TODO : potentially problematic...
        return !(status.equals(OnlineStatus.OFFLINE) || status.equals(OnlineStatus.DO_NOT_DISTURB));
    }

    private List<User> getBlockedUsers(List<User> users, User user) {
        return users.stream().filter(u -> u.blocksNotificationsFrom(user)).collect(Collectors.toList());
    }

    private void dispatchMessagesToUsers(List<User> users) {
        users.forEach(this::trySendMessage);
    }

    private void trySendMessage(User user) {
        try {
            jda.getUserById(user.getDiscordId()).openPrivateChannel().flatMap(
                    channel -> channel.sendMessage("Notified!")
            ).queue();

        }
        catch (Exception ignored) {}
    }
}
