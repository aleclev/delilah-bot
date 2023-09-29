package delilah.services.groupEvent;

import delilah.domain.exceptions.DelilahException;
import delilah.domain.models.groupEvent.GroupEvent;
import delilah.infrastructure.repositories.EventGroupRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;
import java.util.Objects;

@Component
public class GroupEventPruningService {
    @Value("${delilah.lfg.channel.id}")
    private String lfgChannelId;

    @Value("${delilah.lfg.group.max_inactivity}")
    private Integer maxGroupInactivity;

    private final GroupEventService groupEventService;
    private final EventGroupRepository eventGroupRepository;
    private final Clock clock;
    private final JDA jda;

    public GroupEventPruningService(GroupEventService groupEventService, EventGroupRepository eventGroupRepository, Clock clock, JDA jda) {
        this.groupEventService = groupEventService;
        this.eventGroupRepository = eventGroupRepository;
        this.clock = clock;
        this.jda = jda;
    }

    @Scheduled(fixedDelay = 30_000)
    public void pruneInactiveEventGroups() {
        List<GroupEvent> groups = eventGroupRepository.findAll();

        groups.stream()
                .filter(g -> !g.isActive(clock, maxGroupInactivity))
                .forEach(this::deleteGroup);

        eventGroupRepository.findAll().forEach(groupEventService::updateEmbed);
    }

    public GroupEvent deleteGroup(GroupEvent groupEvent) {
        eventGroupRepository.delete(groupEvent);
        deleteDiscordEventGroupMessage(groupEvent.getId());
        return groupEvent;
    }

    public GroupEvent deleteGroup(String eventGroupId) {
        GroupEvent groupEvent = eventGroupRepository.findById(eventGroupId);
        return deleteGroup(groupEvent);
    }

    private void deleteDiscordEventGroupMessage(String messageId) {
        TextChannel lfgChannel = (TextChannel) jda.getGuildChannelById(ChannelType.TEXT, lfgChannelId);
        lfgChannel.deleteMessageById(messageId).queue();
    }

    public void deleteGroupAs(String userId, String groupId) {
        GroupEvent groupEvent = eventGroupRepository.findById(groupId);

        if (Objects.isNull(groupEvent)) throw new DelilahException("Error while fetching group!");

        if (!groupEvent.getOwnerId().equals(userId)) throw new DelilahException("Only the group owner can delete a group.");

        this.deleteGroup(groupEvent);
    }
}
