package delilah.domain.models.groupEvent;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Document
public class Activity {

    private String longName;

    @Id
    private String shortName;

    private List<String> relatedActivities;

    public Activity(String longName, String shortName) {
        this.longName = longName;
        this.shortName = shortName;
    }

    public List<String> getAllRelatedActivities() {
        List<String> temp = new ArrayList<>();
        if (Objects.nonNull(relatedActivities))
            temp.addAll(relatedActivities);
        temp.add(shortName);
        return temp;
    }
}
