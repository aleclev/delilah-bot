package delilah.domain.factories;

import delilah.domain.models.dictionnary.Dictionary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class DictionaryFactory {

    public Dictionary createDefaultDictionary(String dictionaryId){
        return new Dictionary(dictionaryId, new ArrayList<>());
    }

    public Dictionary createDefaultRootDictionary() {
        String dictionaryId = UUID.randomUUID().toString();

        return createDefaultDictionary(dictionaryId);
    }
}
