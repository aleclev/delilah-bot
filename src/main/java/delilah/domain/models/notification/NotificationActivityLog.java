package delilah.domain.models.notification;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class NotificationActivityLog {

    private Instant lastActivity;

    public void logActivity(Clock clock) {
        lastActivity = Instant.now(clock);
    }

    public NotificationActivityLog() {
    }

    public Duration timeElapsedSinceLastActivity(Clock clock) {
        return Objects.isNull(lastActivity) ? Duration.ofDays(999999) : Duration.between(lastActivity, Instant.now(clock));
    }
}
