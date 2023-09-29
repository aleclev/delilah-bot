package delilah.domain.models.groupEvent;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Activity {

    private String longName;

    @Id
    private String shortName;

    public Activity(String longName, String shortName) {
        this.longName = longName;
        this.shortName = shortName;
    }
}
