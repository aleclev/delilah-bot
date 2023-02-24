package delilah.services.lookingForGroup;

import delilah.domain.exceptions.LookingForGroupException;
import delilah.domain.factories.EventGroupFactory;
import delilah.domain.models.lookingForGroup.Activity;
import delilah.domain.models.lookingForGroup.EventGroup;
import delilah.domain.models.notification.NotificationBroadcastReport;
import delilah.infrastructure.repositories.ActivityRepository;
import delilah.infrastructure.repositories.EventGroupRepository;
import delilah.services.NotificationBroadcastService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class LookingForGroupService {

    private HashMap<String, String> messageIdToGroupId = new HashMap<>();
    @Autowired
    private EventGroupFactory eventGroupFactory;

    @Autowired
    private LFGSummonService lfgSummonService;

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

    public String createEventGroup(String ownerId, String title, String description, Integer maxSize) throws ExecutionException, InterruptedException {

        Activity activity = activityRepository.findById(title);

        if (Objects.isNull(activity)) activity = new Activity("", title);

        EventGroup group = eventGroupFactory.createEventGroup(ownerId, activity, description, maxSize);

        GroupEventEmbedBuilder builder = new GroupEventEmbedBuilder();
        builder.fromEventGroup(group);

        this.eventGroupRepository.save(group);

        var message = (getLfgChannel()).sendMessageEmbeds(builder.build()).addActionRow(getActionRow1()).addActionRow(getActionRow2())
                .mapToResult().submit().get().get();

        messageIdToGroupId.put(message.getId(), group.getId());

        return message.getJumpUrl();
    }

    public void joinGroup(String discordId, String messageId) {

        String groupId = messageIdToGroupId.get(messageId);
        EventGroup group = eventGroupRepository.findById(groupId);

        group.joinGroup(discordId);

        updateEmbed(messageId, group);
    }

    public void joinGroupAlternate(String discordId, String messageId) {

        String groupId = messageIdToGroupId.get(messageId);
        EventGroup group = eventGroupRepository.findById(groupId);

        group.joinGroupAsReserve(discordId);

        updateEmbed(messageId, group);
    }

    public void summonGroup(String discordId, String messageId) {

        EventGroup group = getGroupByMessageId(messageId);

        if (!discordId.equals(group.getOwnerId())) throw new LookingForGroupException("Only the group owner can summon the group.");

        String messageUrl = getLfgChannel().getJumpUrl() + "/" + messageId;

        lfgSummonService.summonGroup(group, messageUrl);
    }

    private EventGroup getGroupByMessageId(String messageId) {
        String groupId = messageIdToGroupId.get(messageId);

        if (Objects.isNull(groupId)) throw new LookingForGroupException("This group is inactive");

        EventGroup group = eventGroupRepository.findById(groupId);

        if (Objects.isNull(group)) throw new LookingForGroupException("Error finding event.");

        return group;
    }

    public void leaveGroup(String discordId, String messageId) {

        String groupId = messageIdToGroupId.get(messageId);
        EventGroup group = eventGroupRepository.findById(groupId);

        group.leaveGroup(discordId);

        updateEmbed(messageId, group);
    }

    public NotificationBroadcastReport sendAlert(String discordId, String messageId, String messageUrl) throws ExecutionException, InterruptedException {

        EventGroup group = getGroupByMessageId(messageId);

        if (!discordId.equals(group.getOwnerId())) throw new LookingForGroupException("Only the group owner can send alerts.");

        return broadcastService.broadCastToTagsAsUser(
                List.of(group.getActivity().getShortName()),
                group.getDescription(),
                discordId,
                getLfgChannel().getGuild(),
                clock,
                messageUrl);

    }

    private void updateEmbed(String messageId, EventGroup eventGroup) {

        GroupEventEmbedBuilder builder = new GroupEventEmbedBuilder();

        getLfgChannel().editMessageEmbedsById(messageId, builder.fromEventGroup(eventGroup).build()).queue();
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
                Button.primary("lfg_alert", "Alert")
        );
    }
}
