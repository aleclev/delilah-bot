package delilah.domain.lookingForGroup;

import delilah.domain.exceptions.LookingForGroupException;
import lombok.Getter;

import java.util.List;

@Getter
public class EventGroup {

    private String id;
    private String ownerId;

    private String title;
    private String description;
    private List<String> participantsIds;
    private List<String> reserveIds;
    private Integer maxSize;

    public EventGroup(String id, String ownderId, String title, String description, List<String> participantsIds, List<String> reserveIds, Integer maxSize) {
        this.id = id;
        this.ownerId = ownderId;
        this.title = title;
        this.description = description;
        this.participantsIds = participantsIds;
        this.reserveIds = reserveIds;
        this.maxSize = maxSize;
    }

    public void joinGroup(String discordId) {

        if (participantsIds.contains(discordId)) throw new LookingForGroupException("You are already in this group.");

        if (atCapacity()) throw new LookingForGroupException("Group at capacity.");

        reserveIds.remove(discordId);
        participantsIds.add(discordId);
    }

    public void joinGroupAsReserve(String discordId) {

        if (participantsIds.contains(discordId)) throw new LookingForGroupException("You are already in this group.");

        participantsIds.remove(discordId);
        reserveIds.add(discordId);
    }

    public void leaveGroup(String discordId) {

        participantsIds.remove(discordId);
        reserveIds.remove(discordId);
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
}
