package delilah.factories;

import delilah.models.dictionnary.Dictionary;
import delilah.models.dictionnary.DictionaryEntry;
import delilah.models.dictionnary.DictionaryPermissions;

import java.util.Map;
import java.util.TreeMap;

public class DictionaryFactory {
    public Dictionary createEmptyDictionary(String dictionaryId, DictionaryPermissions dictionaryPermissions){
        Map entries = new TreeMap<String, DictionaryEntry>();
        return new Dictionary(dictionaryId, dictionaryPermissions, entries);
    }
}
