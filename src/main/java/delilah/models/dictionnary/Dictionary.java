package delilah.models.dictionnary;

import java.util.Map;

public class Dictionary {

    private String dictionaryId;

    private DictionaryPermissions dictionaryPermissions;

    private Map<String, DictionaryEntry> entries;

    public Dictionary() {

    }

    public Dictionary(String dictionaryId, DictionaryPermissions dictionaryPermissions, Map<String, DictionaryEntry> entries) {
        this.dictionaryId = dictionaryId;
        this.dictionaryPermissions = dictionaryPermissions;
        this.entries = entries;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public DictionaryPermissions getDictionaryPermissions() {
        return dictionaryPermissions;
    }

    public Map<String, DictionaryEntry> getEntries() {
        return entries;
    }
}
