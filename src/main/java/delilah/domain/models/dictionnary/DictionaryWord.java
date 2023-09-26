package delilah.domain.models.dictionnary;

import lombok.Getter;

@Getter
public class DictionaryWord {

    private final String word;

    public DictionaryWord(String word) {
        this.word = word;
    }

    public boolean isSimilarTo(DictionaryWord word2) {
        return this.word.contains(word2.word) || word2.word.contains(this.word);
    }

    @Override
    public boolean equals(Object otherWord) {
        if (! (otherWord instanceof DictionaryWord)) return false;

        else return this.word.equals(((DictionaryWord) otherWord).word);
    }

    @Override
    public String toString() {
        return word;
    }
}
