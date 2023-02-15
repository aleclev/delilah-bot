package delilah.domain.models.dictionnary;

public class DictionaryWord {

    private final String word;

    public DictionaryWord(String word) {
        this.word = word;
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
