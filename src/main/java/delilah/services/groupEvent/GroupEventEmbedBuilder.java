package delilah.services.groupEvent;

import delilah.domain.models.groupEvent.GroupEvent;
import delilah.util.DiscordTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.TimeFormat;
import net.dv8tion.jda.api.utils.Timestamp;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Clock;
import java.util.List;

@Component
public class GroupEventEmbedBuilder extends EmbedBuilder {

    private final Clock clock;

    public GroupEventEmbedBuilder(Clock clock) {
        this.clock = clock;
    }

    public GroupEventEmbedBuilder fromEventGroup(GroupEvent groupEvent) {

        DiscordTime startTime = new DiscordTime(groupEvent.getStartTime(), TimeFormat.RELATIVE);
        DiscordTime lastActivityTime = new DiscordTime(groupEvent.getLastActivity(), TimeFormat.RELATIVE);

        this.setColor(groupEvent)
            .setTitle(groupEvent.getTitle())
            .setDescription(groupEvent.getDescription())
            .addField(
                    String.format("Members (%s/%s)", groupEvent.getParticipantsIds().size(), groupEvent.getMaxSize()),
                    discordIdsAsMentions(groupEvent.getParticipantsIds()),
                    false
            )
            .addField(
                    "Reserve",
                    discordIdsAsMentions(groupEvent.getReserveIds()),
                    false
                    )
            .addField(
                    "Owner",
                    String.format("<@%s>", groupEvent.getOwnerId()),
                    false
            )
            .addField(
                    "Start time",
                    startTime.toString(),
                    true
            )
            .addField(
                    "Latest Interaction",
                    lastActivityTime.toString(),
                    true
            );

        return this;
    }

    public String discordIdsAsMentions(List<String> discorIds) {

        if (discorIds.isEmpty()) return "No members";

        StringBuilder sb = new StringBuilder();
        discorIds.stream().forEach(id -> sb.append(String.format("<@%s>\n", id)));

        return sb.toString();
    }

    public EmbedBuilder setColor(GroupEvent groupEvent) {

        if (groupEvent.atCapacity() && groupEvent.isStarted(clock))
            return setColor(Color.GREEN);
        if (!groupEvent.isStarted(clock) && !groupEvent.atCapacity())
            return setColor(Color.RED);
        if (!groupEvent.isStarted(clock) && groupEvent.atCapacity())
            return setColor(Color.BLUE);
        return setColor(Color.YELLOW);
    }
}
