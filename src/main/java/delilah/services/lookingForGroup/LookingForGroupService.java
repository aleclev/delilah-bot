package delilah.services.lookingForGroup;

import delilah.domain.factories.EventGroupFactory;
import delilah.domain.lookingForGroup.EventGroup;
import delilah.infrastructure.repositories.EventGroupRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Component
public class LookingForGroupService {

    private HashMap<String, String> messageIdToGroupId = new HashMap<>();
    @Autowired
    private EventGroupFactory eventGroupFactory;

    @Value("${delilah.lfg.channel.id}")
    private String lfgChannelId;

    @Autowired
    JDA jda;

    @Autowired
    private EventGroupRepository eventGroupRepository;

    public void createEventGroup(String ownerId, String title, String description, Integer maxSize) throws ExecutionException, InterruptedException {

        EventGroup group = eventGroupFactory.createEventGroup(ownerId, title, description, maxSize);

        GroupEventEmbedBuilder builder = new GroupEventEmbedBuilder();
        builder.fromEventGroup(group);

        this.eventGroupRepository.save(group);

        var message = (getLfgChannel()).sendMessageEmbeds(builder.build()).addActionRow(getActionRows())
                .mapToResult().submit().get().get();

        messageIdToGroupId.put(message.getId(), group.getId());

        jda.getUserById(ownerId).openPrivateChannel().flatMap(
                channel -> channel.sendMessage(message.getJumpUrl())
        ).queue();

    }

    public void joinGroup(String discordId, String messageId) {

        String groupId = messageIdToGroupId.get(messageId);
        EventGroup group = eventGroupRepository.findById(groupId);

        group.joinGroup(discordId);

        updateEmbed(messageId, group);
    }

    public void leaveGroup(String discordId, String messageId) {

        String groupId = messageIdToGroupId.get(messageId);
        EventGroup group = eventGroupRepository.findById(groupId);

        group.leaveGroup(discordId);

        updateEmbed(messageId, group);
    }

    private void updateEmbed(String messageId, EventGroup eventGroup) {

        GroupEventEmbedBuilder builder = new GroupEventEmbedBuilder();

        getLfgChannel().editMessageEmbedsById(messageId, builder.fromEventGroup(eventGroup).build()).queue();
    }

    private TextChannel getLfgChannel() {
        return (TextChannel)jda.getGuildChannelById(ChannelType.TEXT, lfgChannelId);
    }

    private Collection<ItemComponent> getActionRows() {
        return
            List.of(
                Button.secondary("lfg_join", "Join"),
                Button.secondary("lfg_leave", "Leave")
            );
    }
}
