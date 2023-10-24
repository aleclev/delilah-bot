package delilah.infrastructure.repositories;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class ConfigVariable {

    @Id
    private String id;

    private String value;

    public ConfigVariable(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
