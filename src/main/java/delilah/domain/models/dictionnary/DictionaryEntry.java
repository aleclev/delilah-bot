package delilah.domain.models.dictionnary;

import lombok.Getter;

@Getter
public class DictionaryEntry {

    private DictionaryWord word;

    private DictionaryDefinition definition;

    public DictionaryEntry(DictionaryWord word, DictionaryDefinition definition) {
        this.word = word;
        this.definition = definition;
    }

    public boolean isWord(DictionaryWord otherWord) {
        return word.equals(otherWord);
    }

    public boolean isSimilarWord(DictionaryWord word2) {
        return word.isSimilarTo(word2);
    }

    @Override
    public String toString() {
        return String.format("Word: %s\nDefinition: %s", word, definition);
    }
}
