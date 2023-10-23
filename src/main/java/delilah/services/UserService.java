package delilah.services;

import delilah.domain.factories.DictionaryFactory;
import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final DictionaryFactory dictionaryFactory;
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    public UserService(DictionaryFactory dictionaryFactory, UserFactory userFactory, UserRepository userRepository) {
        this.dictionaryFactory = dictionaryFactory;
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public User getOrRegisterUserByDiscordId(String discordId) {
        User user = userRepository.findByDiscordId(discordId);

        if (user != null) return user;

        return registerUser(discordId);
    }

    public boolean createUserIfNotExists(String discordId) {
        User user = userRepository.findByDiscordId(discordId);

        if (user != null) return false;

        registerUser(discordId);
        return true;
    }

    public User registerUser(String discordId) {

        Dictionary dictionary = dictionaryFactory.createDefaultRootDictionary();
        User user = userFactory.createUser(discordId, dictionary);
        userRepository.save(user);
        user.setRootDictionary(dictionary);
        userRepository.save(user);

        return user;
    }
}
