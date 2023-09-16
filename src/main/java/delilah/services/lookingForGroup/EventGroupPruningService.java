package delilah.services.lookingForGroup;

import delilah.domain.models.lookingForGroup.EventGroup;
import delilah.infrastructure.repositories.EventGroupRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

@Component
public class EventGroupPruningService {

    @Value("${delilah.lfg.prune.delay}")
    private static Integer pruneDelay;
    @Value("${delilah.lfg.group.max_inactivity}")
    private Integer maxGroupInactivityTime;
    @Value("${delilah.lfg.channel.id}")
    private String lfgChannelId;
    private final EventGroupRepository eventGroupRepository;
    private final Clock clock;
    private final JDA jda;

    public EventGroupPruningService(EventGroupRepository eventGroupRepository, Clock clock, JDA jda) {
        this.eventGroupRepository = eventGroupRepository;
        this.clock = clock;
        this.jda = jda;
    }

    @Scheduled(fixedDelay = 60_000)
    public void pruneInactiveEventGroups() {
        List<EventGroup> groups = eventGroupRepository.findAll();

        groups.stream()
                .filter(g -> g.timeSinceLastActivity(clock).getSeconds() > maxGroupInactivityTime)
                .forEach(this::deleteGroup);
    }

    public EventGroup deleteGroup(EventGroup eventGroup) {
        eventGroupRepository.delete(eventGroup);
        deleteDiscordEventGroupMessage(eventGroup.getId());
        return eventGroup;
    }

    public EventGroup deleteGroup(String eventGroupId) {
        EventGroup eventGroup = eventGroupRepository.findById(eventGroupId);
        return deleteGroup(eventGroup);
    }

    private void deleteDiscordEventGroupMessage(String messageId) {
        TextChannel lfgChannel = (TextChannel) jda.getGuildChannelById(ChannelType.TEXT, lfgChannelId);
        lfgChannel.deleteMessageById(messageId).queue();
    }
}
