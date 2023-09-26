package delilah.domain.models.dictionnary;

import lombok.Getter;

@Getter
public class DictionaryDefinition {

    private String definition;

    public DictionaryDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return definition;
    }
}
