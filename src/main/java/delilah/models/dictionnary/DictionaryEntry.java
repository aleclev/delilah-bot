package delilah.models.dictionnary;

public class DictionaryEntry {

    private final String dictionaryEntryId;
    private String content;

    public DictionaryEntry(String dictionaryEntryId, String definition) {
        this.dictionaryEntryId = dictionaryEntryId;
        this.content = definition;
    }
}
