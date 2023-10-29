package delilah.domain.factories;

import delilah.domain.exceptions.groupEvent.InvalidGroupEventSizeException;
import delilah.domain.models.groupEvent.Activity;
import delilah.domain.models.groupEvent.GroupEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupEventFactory {

    public final int GROUP_MAX_SIZE = 24;
    public final int GROUP_MIN_SIZE = 1;

    private final Clock clock;

    public GroupEventFactory(Clock clock) {
        this.clock = clock;
    }

    public GroupEvent createEventGroup(String id, String ownerId, Activity activity, String description,
                                       Integer maxSize, Instant startTime) {

        if (maxSize < GROUP_MIN_SIZE || maxSize > GROUP_MAX_SIZE)
            throw new InvalidGroupEventSizeException(String.format("Error! Size must be between %s and %s", GROUP_MIN_SIZE, GROUP_MAX_SIZE));

        List<String> participantsId = new ArrayList<>();
        participantsId.add(ownerId);
        Instant lastActivity = clock.instant();
        return new GroupEvent(id, ownerId, activity, description, participantsId, new ArrayList<>(), maxSize,
                lastActivity, startTime);
    }
}
