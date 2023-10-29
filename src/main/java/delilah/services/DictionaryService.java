package delilah.services;

import delilah.domain.exceptions.dictionary.DuplicateEntryException;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.dictionnary.DictionaryDefinition;
import delilah.domain.models.dictionnary.DictionaryEntry;
import delilah.domain.models.dictionnary.DictionaryWord;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryService {

    @Autowired
    UserRepository userRepository;

    public DictionaryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addToUserDictionary(String userDiscordId, String stringWord, String stringDefinition)
            throws DuplicateEntryException {
        User user = userRepository.findById(userDiscordId);

        DictionaryWord word = new DictionaryWord(stringWord);
        DictionaryDefinition definition = new DictionaryDefinition(stringDefinition);

        DictionaryEntry newEntry = new DictionaryEntry(word, definition);

        user.getRootDictionary().addEntry(newEntry);

        userRepository.save(user);
    }

    public DictionaryEntry removeDictionaryEntryForUser(String discordId, String word) {
        User user = userRepository.findById(discordId);

        DictionaryEntry entry = user.getRootDictionary().getEntryByWord(word);

        var temp = user.getRootDictionary().removeEntry(entry);
        userRepository.save(user);

        return temp;
    }

    public DictionaryEntry getDictionaryEntryForUser(String userDiscordId, String word) {
        User user = userRepository.findById(userDiscordId);

        return user.getRootDictionary().getEntryByWord(word);
    }

    public List<DictionaryEntry> getEntriesLikeInputForUser(String discordId, String input) {
        User user = userRepository.findById(discordId, true);

        DictionaryWord compareWord = new DictionaryWord(input);
        return user.getRootDictionary().findEntriesSimilarTo(compareWord);
    }

    public Dictionary getDictionaryForUser(String userDiscordId) {
        User user = userRepository.findById(userDiscordId);

        return user.getRootDictionary();
    }
}
