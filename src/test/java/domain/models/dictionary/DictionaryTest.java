package domain.models.dictionary;

import delilah.domain.exceptions.dictionary.DuplicateEntryException;
import delilah.domain.exceptions.dictionary.EntryNotFoundException;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.dictionnary.DictionaryDefinition;
import delilah.domain.models.dictionnary.DictionaryEntry;
import delilah.domain.models.dictionnary.DictionaryWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DictionaryTest {

    private static final String ID = "1234567890";
    private static final String GENERIC_WORD = "hello";
    private static final DictionaryEntry GENERIC_ENTRY
            = new DictionaryEntry(new DictionaryWord(GENERIC_WORD), new DictionaryDefinition("awd"));
    private static final String GENERIC_WORD_UPPER = "HELLO";

    private Dictionary dictionary;

    @BeforeEach
    public void setup() {
        dictionary = new Dictionary(ID, new ArrayList<>());
    }

    @Test
    public void givenEmptyDictionary_whenAddingEntry_thenEntryIsAdded() {

        dictionary.addEntry(GENERIC_ENTRY);

        assertThat(dictionary.getEntries()).contains(GENERIC_ENTRY);
    }

    @Test
    public void givenDuplicateWord_whenAddingEntry_thenThrowsDuplicateEntryException() {

        dictionary.addEntry(GENERIC_ENTRY);

        assertThrows(DuplicateEntryException.class,
                () -> dictionary.addEntry(GENERIC_ENTRY));
    }

    @Test
    public void givenEmptyDictionary_whenFindingEntry_thenThrowsEntryNotFoundException() {

        assertThrows(EntryNotFoundException.class,
                () -> dictionary.getEntryByWord(GENERIC_WORD));
    }

    @Test
    public void givenDictionaryWithEntry_whenFindingEntry_thenEntryReturned() {

        dictionary.addEntry(GENERIC_ENTRY);

        DictionaryEntry entry = dictionary.getEntryByWord(GENERIC_WORD);

        assertEquals(GENERIC_ENTRY, entry);
    }

}
