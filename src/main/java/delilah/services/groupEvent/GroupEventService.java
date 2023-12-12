package delilah.services.groupEvent;

import delilah.domain.exceptions.DelilahException;
import delilah.domain.exceptions.groupEvent.GroupEventException;
import delilah.domain.factories.GroupEventFactory;
import delilah.domain.models.groupEvent.Activity;
import delilah.domain.models.groupEvent.GroupEvent;
import delilah.domain.models.notification.NotificationBroadcastReport;
import delilah.infrastructure.repositories.ActivityRepository;
import delilah.infrastructure.repositories.EventGroupRepository;
import delilah.services.NotificationBroadcastService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class GroupEventService {
    @Autowired
    private GroupEventFactory groupEventFactory;

    @Autowired
    private GroupEventSummonService groupEventSummonService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private NotificationBroadcastService broadcastService;

    @Autowired
    private Clock clock;

    @Value("${delilah.lfg.channel.id}")
    private String lfgChannelId;

    @Autowired
    JDA jda;

    @Autowired
    private EventGroupRepository eventGroupRepository;

    public String createEventGroup(String ownerId, String title, String description, Integer maxSize,
                                   Instant startTime) throws ExecutionException, InterruptedException {

        Activity activity = activityRepository.findById(title);

        if (Objects.isNull(activity)) throw new DelilahException("Invalid activity name!");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Preparing group");

        var message = (getLfgChannel()).sendMessageEmbeds(eb.build()).addActionRow(getActionRow1()).addActionRow(getActionRow2())
                .mapToResult().submit().get().get();

        GroupEvent group = groupEventFactory.createEventGroup(message.getId(), ownerId, activity, description, maxSize, startTime);

        this.eventGroupRepository.save(group);

        updateEmbed(message.getId(), group);

        return message.getJumpUrl();
    }

    public void joinGroup(String discordId, String messageId) {

        GroupEvent group = eventGroupRepository.findById(messageId);

        group.joinGroup(discordId);
        group.logActivityAtTime(clock.instant());
        eventGroupRepository.save(group);

        updateEmbed(messageId, group);
    }

    public void joinGroupAlternate(String discordId, String messageId) {

        GroupEvent group = eventGroupRepository.findById(messageId);

        group.joinGroupAsReserve(discordId);
        group.logActivityAtTime(clock.instant());
        eventGroupRepository.save(group);

        updateEmbed(messageId, group);
    }

    public void summonGroup(String discordId, String messageId) {

        GroupEvent group = getGroupByMessageId(messageId);

        if (!discordId.equals(group.getOwnerId())) throw new GroupEventException("Only the group owner can summon the group.");

        String messageUrl = getLfgChannel().getJumpUrl() + "/" + messageId;

        groupEventSummonService.summonGroup(group, messageUrl);

        group.logActivityAtTime(clock.instant());
        eventGroupRepository.save(group);
        updateEmbed(messageId, group);
    }

    private GroupEvent getGroupByMessageId(String messageId) {

        GroupEvent group = eventGroupRepository.findById(messageId);

        if (Objects.isNull(group)) throw new GroupEventException("Error finding event.");

        return group;
    }

    public void leaveGroup(String discordId, String messageId) {

        GroupEvent group = eventGroupRepository.findById(messageId);

        group.leaveGroup(discordId);

        group.logActivityAtTime(clock.instant());
        eventGroupRepository.save(group);
        updateEmbed(messageId, group);
    }

    public NotificationBroadcastReport sendAlert(String discordId, String messageId, String messageUrl) throws ExecutionException, InterruptedException {

        GroupEvent group = getGroupByMessageId(messageId);

        if (!discordId.equals(group.getOwnerId())) throw new GroupEventException("Only the group owner can send alerts.");

        return broadcastService.broadCastToTagsAsUser(
                group.getActivity().getAllRelatedActivities(),
                group.getDescription(),
                discordId,
                getLfgChannel().getGuild(),
                clock,
                messageUrl);

    }

    public void updateEmbed(GroupEvent groupEvent) {
        updateEmbed(groupEvent.getId(), groupEvent);
    }

    private void updateEmbed(String messageId, GroupEvent groupEvent) {

        GroupEventEmbedBuilder builder = new GroupEventEmbedBuilder(clock);

        getLfgChannel().editMessageEmbedsById(messageId, builder.fromEventGroup(groupEvent).build()).queue();
    }

    private TextChannel getLfgChannel() {
        return (TextChannel)jda.getGuildChannelById(ChannelType.TEXT, lfgChannelId);
    }

    private Collection<ItemComponent> getActionRow1() {
        return
            List.of(
                Button.success("lfg_join", "Join"),
                Button.danger("lfg_leave", "Leave"),
                Button.secondary("lfg_join_alternate", "Alternate")
            );
    }

    private Collection<ItemComponent> getActionRow2() {

        return List.of(
                Button.primary("lfg_summon", "Summon Group"),
                Button.primary("lfg_alert", "Alert"),
                Button.danger("lfg_delete", "Delete")
        );
    }
}
