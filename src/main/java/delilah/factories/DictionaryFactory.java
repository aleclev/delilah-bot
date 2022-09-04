package delilah.factories;

import delilah.models.dictionnary.Dictionary;
import delilah.models.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Component
public class DictionaryFactory {

    public Dictionary createDefaultDictionary(String dictionaryId, User owner){
        Map<String, String> entries = new TreeMap<String, String>();
        return new Dictionary(dictionaryId, owner, entries);
    }

    public Dictionary createDefaultRootDictionary() {
        String dictionaryId = UUID.randomUUID().toString();

        return createDefaultDictionary(dictionaryId, null);
    }
}
