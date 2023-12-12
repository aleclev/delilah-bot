package delilah.domain.models.groupEvent;

import delilah.domain.exceptions.groupEvent.GroupEventException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Getter
@Document
public class GroupEvent {

    @Id
    private String id;
    private String ownerId;

    private Activity activity;
    private String description;
    private List<String> participantsIds;
    private List<String> reserveIds;
    private Integer maxSize;

    private Instant startTime;
    private Instant lastActivity;

    public GroupEvent(String id, String ownerId, Activity activity, String description, List<String> participantsIds,
                      List<String> reserveIds, Integer maxSize, Instant lastActivity, Instant startTime) {
        this.id = id;
        this.activity = activity;
        this.ownerId = ownerId;
        this.description = description;
        this.participantsIds = participantsIds;
        this.reserveIds = reserveIds;
        this.maxSize = maxSize;
        this.lastActivity = lastActivity;
        this.startTime = startTime;
    }

    public void joinGroup(String discordId) {

        if (participantsIds.contains(discordId)) throw new GroupEventException("You are already in this group.");

        if (atCapacity()) throw new GroupEventException("Group at capacity.");

        reserveIds.remove(discordId);
        participantsIds.add(discordId);
    }

    public void joinGroupAsReserve(String discordId) {

        if (participantsIds.contains(discordId) || reserveIds.contains(discordId)) throw new GroupEventException("You are already in this group.");

        participantsIds.remove(discordId);
        reserveIds.add(discordId);
    }

    public void leaveGroup(String discordId) {

        participantsIds.remove(discordId);
        reserveIds.remove(discordId);
    }

    public void logActivityAtTime(Instant instant) {

        lastActivity = instant;
    }

    private Duration timeSinceLastActivity(Clock clock) {

        return Duration.between(lastActivity, clock.instant());
    }

    public boolean isActive(Clock clock, Integer maxInactivitySeconds) {
        return !isStarted(clock) || !isExceedingMaxInactivityThreshold(clock, maxInactivitySeconds);
    }

    public boolean isExceedingMaxInactivityThreshold(Clock clock, Integer maxInactivitySeconds) {
        return Duration.between(lastActivity, clock.instant()).getSeconds() > maxInactivitySeconds;
    }

    public boolean isStarted(Clock clock) {
        return clock.instant().isAfter(startTime);
    }

    public boolean userWithIdInParticipants(String discordId) {

        return participantsIds.contains(discordId);
    }

    public boolean userWithIdInReserve(String discordId) {

        return reserveIds.contains(discordId);
    }
    public boolean atCapacity() {
        return participantsIds.size() >= maxSize;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return activity.getLongName();
    }
}
