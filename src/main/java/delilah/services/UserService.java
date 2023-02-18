package delilah.services;

import delilah.domain.factories.DictionaryFactory;
import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    DictionaryFactory dictionaryFactory;
    @Autowired
    UserFactory userFactory;

    @Autowired
    UserRepository userRepository;

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
