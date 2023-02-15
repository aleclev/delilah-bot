package delilah.domain.models.dictionnary;

public class DictionaryEntry {

    private DictionaryWord word;

    private DictionaryDefinition definition;

    public boolean isWord(DictionaryWord otherWord) {
        return word.equals(otherWord);
    }

    @Override
    public String toString() {
        return word.toString();
    }
}
