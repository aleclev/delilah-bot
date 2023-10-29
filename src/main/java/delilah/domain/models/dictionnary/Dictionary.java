package delilah.domain.models.dictionnary;

import delilah.domain.exceptions.dictionary.DuplicateEntryException;
import delilah.domain.exceptions.dictionary.EntryNotFoundException;
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


    public void addEntry(DictionaryEntry entry) throws DuplicateEntryException {
        if (wordExists(entry.getWord()))
            throw new DuplicateEntryException("Error! Word already exists. Please remove this word to change it.");

        entries.add(entry);
    }

    public DictionaryEntry removeEntry(DictionaryEntry entry) {
        entries.remove(entry);
        return entry;
    }

    public DictionaryEntry getEntryByWord(String word) throws EntryNotFoundException {
        DictionaryWord compareWord = new DictionaryWord(word);
        return entries.stream().filter(e -> e.isWord(compareWord)).findFirst()
                .orElseThrow(() -> new EntryNotFoundException("Error! Entry not found."));
    }

    public boolean wordExists(DictionaryWord word) {
        return getWords().stream().anyMatch(word::equals);
    }

    public List<DictionaryEntry> findEntriesSimilarTo(DictionaryWord word) {
        return getEntries().stream().filter(e -> e.isSimilarWord(word)).collect(Collectors.toList());
    }

    List<DictionaryWord> getWords() {
        return entries.stream().map(DictionaryEntry::getWord).collect(Collectors.toList());
    }
    private Dictionary() {}
}
