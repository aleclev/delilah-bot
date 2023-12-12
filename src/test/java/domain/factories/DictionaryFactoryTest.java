package domain.factories;

import delilah.domain.factories.DictionaryFactory;
import delilah.domain.models.dictionnary.Dictionary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DictionaryFactoryTest {

    private DictionaryFactory dictionaryFactory;

    @BeforeEach
    public void setup() {
        dictionaryFactory = new DictionaryFactory();
    }

    @Test
    public void whenCreatingDefaultRootDictionary_thenDictionaryCreatedWithNoEntries() {
        Dictionary dictionary = dictionaryFactory.createDefaultRootDictionary();

        Assertions.assertThat(dictionary.getEntries()).isEmpty();
    }
}
