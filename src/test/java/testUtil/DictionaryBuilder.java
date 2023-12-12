package testUtil;

import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.dictionnary.DictionaryEntry;

import java.util.ArrayList;
import java.util.List;

public class DictionaryBuilder {

    private String id = "123456";
    private ArrayList<DictionaryEntry> entries = new ArrayList<>();

    public Dictionary build() {
        return new Dictionary(id, entries);
    }
}
