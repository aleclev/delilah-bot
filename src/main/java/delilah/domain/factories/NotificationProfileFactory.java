package delilah.domain.factories;

import delilah.domain.models.notification.NotificationActivityLog;
import delilah.domain.models.notification.NotificationProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NotificationProfileFactory {

    public NotificationProfile createDefault() {
        return new NotificationProfile(new ArrayList<>(), new NotificationActivityLog(), new ArrayList<>());
    }
}
