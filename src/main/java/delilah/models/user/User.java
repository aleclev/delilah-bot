package delilah.models.user;


import delilah.models.dictionnary.Dictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class User {

    @Id
    String discordId;

    @Column
    String userId;

    @OneToMany
    List<Dictionary> dictionaries;

    @OneToOne
    Dictionary rootDictionary;

    public User(String discordId, String userId, List<Dictionary> dictionaries, Dictionary rootDictionary) {
        this.discordId = discordId;
        this.userId = userId;
        this.dictionaries = dictionaries;
        this.rootDictionary = rootDictionary;
    }

    public void setRootDictionary(Dictionary dictionary) {
        rootDictionary = dictionary;
    }

    public void addDictionary(Dictionary dictionary) {
        if (dictionaries.size() >= 10) return;

        dictionaries.add(dictionary);
    }

    private User() {}

    public Dictionary getDictionaryById(String id) {
        return dictionaries.stream().filter(d -> d.getDictionaryId().equals(id)).findFirst().get();
    }
}
