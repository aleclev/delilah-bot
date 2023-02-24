package delilah.services;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import delilah.domain.exceptions.NotificationCooldownException;
import delilah.domain.models.notification.NotificationBroadcastReport;
import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.IThreadContainer;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO : Make this class smaller
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

    public NotificationBroadcastReport broadCastToTagsAsUser(List<String> tags, String message, String discordId, Guild guild, Clock clock, String messageUrl)
            throws NotificationCooldownException, ExecutionException, InterruptedException {
        User user = userService.getOrRegisterUserByDiscordId(discordId);

        Duration minimumDelayForNotification = Duration.of(minimumMinuteDelay, ChronoUnit.MINUTES);

        if (user.isAllowedToBroadcast(minimumDelayForNotification, clock))
            throw new NotificationCooldownException(String.format("This action has a %s minutes cooldown. Come back later.", minimumMinuteDelay));

        List<NotificationSubscription> subscriptions = tags.stream().map(NotificationSubscription::new).collect(Collectors.toList());

        List<User> subscribedUsers = userRepository.findBySubscriptions(subscriptions);
        List<User> onlineUsers = getOnlineUsers(subscribedUsers, guild);
        List<User> blockingUsers = getBlockingUsers(onlineUsers, user);
        List<User> usersToMessage = new ArrayList<>(onlineUsers);

        var report = new NotificationBroadcastReport(subscriptions, subscribedUsers, blockingUsers, onlineUsers, usersToMessage);


        if (!subscribedUsers.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            subscriptions.forEach(sub -> sb.append("#").append(sub.getTag()).append(" "));
            sb.append(": ");

            //String threadUrl = createThreadAndGetUrl(threadContainer, sb.toString());
            MessageEmbed embed = getEmbed(sb + message, user);
            List<Button> buttons = getMessageButtons(messageUrl, user);


            usersToMessage.removeAll(blockingUsers);
            usersToMessage.retainAll(onlineUsers);
            dispatchMessagesToUsers(usersToMessage, embed, buttons);
            user.logNotificationActivity(clock);

            userRepository.save(user);
        }


        return report;
    }

    private List<Button> getMessageButtons(String threadUrl, User sender) {

        List<Button> buttonRow = new ArrayList<>();
        if (!(threadUrl == null || threadUrl.isEmpty())) buttonRow.add(Button.link(threadUrl, "Go to message"));
        return buttonRow;
    }

    private List<User> getOnlineUsers(List<User> users, Guild guild) {
        return users.stream().filter(u -> userWithIdIsOnline(u.getDiscordId(), guild)).collect(Collectors.toList());
    }

    private boolean userWithIdIsOnline(String discordId, Guild guild) {
        OnlineStatus status = null;
        try {
            status = Objects.requireNonNull(guild.getMemberById(discordId)).getOnlineStatus(); //TODO : potentially problematic...
        } catch (Exception e) {
            return false;
        }
        return !(status.equals(OnlineStatus.OFFLINE) || status.equals(OnlineStatus.DO_NOT_DISTURB));
    }

    private List<User> getBlockingUsers(List<User> users, User user) {
        return users.stream().filter(u -> u.blocksNotificationsFrom(user)).collect(Collectors.toList());
    }

    private void dispatchMessagesToUsers(List<User> users, MessageEmbed embed, List<Button> buttons) {
        users.forEach(u -> trySendMessage(u, embed, buttons));
    }

    private void trySendMessage(User user, MessageEmbed embed, List<Button> buttons) {
        try {
            jda.getUserById(user.getDiscordId()).openPrivateChannel().flatMap(
                    channel -> channel.sendMessageEmbeds(embed).addActionRow(buttons)
            ).queue();
        }
        catch (Exception ignored) {}
    }

    private String createThreadAndGetUrl(IThreadContainer threadContainer, String title) throws ExecutionException, InterruptedException {
        var e = threadContainer.createThreadChannel(title).mapToResult().submit();

        return e.get().get().getJumpUrl();
    }
    private MessageEmbed getEmbed(String title, User user) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(title);
        eb.setDescription("One of your subscribed tags was pinged.");
        eb.addField("Author", String.format("<@%s>", user.getDiscordId()), false);
        eb.setThumbnail(jda.getUserById(user.getDiscordId()).getAvatarUrl());
        return eb.build();
    }
}
