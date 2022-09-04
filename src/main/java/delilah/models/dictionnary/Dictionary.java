package delilah.models.dictionnary;

import delilah.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
@Table
public class Dictionary {

    @Id
    String dictionaryId;

    @ElementCollection
    Map<String, String> entries;

    @ManyToOne
    User owner;

    public Dictionary(String dictionaryId, User owner, Map<String, String> entries) {
        this.dictionaryId = dictionaryId;
        this.entries = entries;
        this.owner = owner;
    }

    private Dictionary() { }

}
