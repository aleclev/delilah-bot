package delilah.infrastructure.repositories;

import delilah.domain.models.user.User;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter
public class CachedUser {

    public static final int MAX_MILLIS_LIFETIME = 60_000;

    private final User user;
    private final Instant timeFetched;

    public CachedUser(User user, Instant timeFetched) {
        this.user = user;
        this.timeFetched = timeFetched;
    }

    public boolean expired(Clock clock) {

        return clock.instant().toEpochMilli() > timeFetched.toEpochMilli() + MAX_MILLIS_LIFETIME;
    }
}
