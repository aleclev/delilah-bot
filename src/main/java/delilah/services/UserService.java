package delilah.services;

import delilah.domain.factories.DictionaryFactory;
import delilah.domain.factories.NotificationProfileFactory;
import delilah.domain.factories.PermissionProfileFactory;
import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final DictionaryFactory dictionaryFactory;
    private final UserFactory userFactory;
    private final NotificationProfileFactory notificationProfileFactory;
    private final PermissionProfileFactory permissionProfileFactory;
    private final UserRepository userRepository;

    public UserService(DictionaryFactory dictionaryFactory,
                       UserFactory userFactory,
                       NotificationProfileFactory notificationProfileFactory,
                       PermissionProfileFactory permissionProfileFactory,
                       UserRepository userRepository) {
        this.dictionaryFactory = dictionaryFactory;
        this.userFactory = userFactory;
        this.notificationProfileFactory = notificationProfileFactory;
        this.permissionProfileFactory = permissionProfileFactory;
        this.userRepository = userRepository;
    }

    public User getOrRegisterUserByDiscordId(String discordId) {
        User user = userRepository.findById(discordId);

        if (user != null) return user;

        return registerUser(discordId);
    }

    public boolean createUserIfNotExists(String discordId) {
        User user = userRepository.findById(discordId);

        if (user != null) return false;

        registerUser(discordId);
        return true;
    }

    public User registerUser(String discordId) {

        Dictionary dictionary = dictionaryFactory.createDefaultRootDictionary();
        NotificationProfile notificationProfile = notificationProfileFactory.createDefault();
        PermissionProfile permissionProfile = permissionProfileFactory.createDefault();
        User user = userFactory.createUser(discordId, dictionary, permissionProfile, notificationProfile);
        userRepository.save(user);

        return user;
    }
}
