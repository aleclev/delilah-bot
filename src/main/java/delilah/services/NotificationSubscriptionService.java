package delilah.services;

import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    public boolean addTagToUserSubscriptions(String discordId, String tag) {
        User user = userService.getOrRegisterUserByDiscordId(discordId);

        if (user.addNotificationSubscription(new NotificationSubscription(tag))) {
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean removeTagFromUserSubscriptions(String discordId, String tag) {

        User user = userService.getOrRegisterUserByDiscordId(discordId);

        if (user.removeNotificationSubscription(new NotificationSubscription(tag))) {
            userRepository.save(user);
            return true;
        }

        return false;
    }


}
