package delilah.domain.models.dictionnary;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Dictionary {

    @Id
    String dictionaryId;

    ArrayList<DictionaryEntry> entries;

    public Dictionary(String dictionaryId, ArrayList<DictionaryEntry> entries) {
        this.dictionaryId = dictionaryId;
        this.entries = entries;
    }


    public boolean addEntry(DictionaryEntry entry) {
        if (findEntry(entry.getWord().toString()) != null) return false;
        entries.add(entry);
        return true;
    }

    public DictionaryEntry removeEntry(DictionaryEntry entry) {
        entries.remove(entry);
        return entry;
    }

    public DictionaryEntry findEntry(String word) {
        DictionaryWord compareWord = new DictionaryWord(word);
        return entries.stream().filter(e -> e.isWord(compareWord)).findFirst().orElse(null);
    }

    public List<DictionaryEntry> findEntriesSimilarTo(DictionaryWord word) {
        return getEntries().stream().filter(e -> e.isSimilarWord(word)).collect(Collectors.toList());
    }
    private Dictionary() {}
}
