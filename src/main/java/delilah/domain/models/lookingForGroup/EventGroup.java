package delilah.domain.models.lookingForGroup;

import delilah.domain.exceptions.LookingForGroupException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Getter
@Document
public class EventGroup {

    @Id
    private String id;
    private String ownerId;

    private Activity activity;
    private String description;
    private List<String> participantsIds;
    private List<String> reserveIds;
    private Integer maxSize;

    private Instant lastActivity;

    public EventGroup(String id, String ownerId, Activity activity, String description, List<String> participantsIds, List<String> reserveIds, Integer maxSize, Instant lastActivity) {
        this.id = id;
        this.activity = activity;
        this.ownerId = ownerId;
        this.description = description;
        this.participantsIds = participantsIds;
        this.reserveIds = reserveIds;
        this.maxSize = maxSize;
        this.lastActivity = lastActivity;
    }

    public void joinGroup(String discordId) {

        if (participantsIds.contains(discordId)) throw new LookingForGroupException("You are already in this group.");

        if (atCapacity()) throw new LookingForGroupException("Group at capacity.");

        reserveIds.remove(discordId);
        participantsIds.add(discordId);
    }

    public void joinGroupAsReserve(String discordId) {

        if (participantsIds.contains(discordId) || reserveIds.contains(discordId)) throw new LookingForGroupException("You are already in this group.");

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

    public Duration timeSinceLastActivity(Clock clock) {

        return Duration.between(lastActivity, clock.instant());
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
