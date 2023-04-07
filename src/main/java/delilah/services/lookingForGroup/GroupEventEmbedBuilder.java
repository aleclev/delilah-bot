package delilah.services.lookingForGroup;

import delilah.domain.models.lookingForGroup.EventGroup;
import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GroupEventEmbedBuilder extends EmbedBuilder {

    public GroupEventEmbedBuilder fromEventGroup(EventGroup eventGroup) {

        this.setTitle(eventGroup.getTitle())
        .setDescription(eventGroup.getDescription())

        .addField(
                String.format("Members (%s/%s)", eventGroup.getParticipantsIds().size(), eventGroup.getMaxSize()),
                discordIdsAsMentions(eventGroup.getParticipantsIds()),
                false
        )

        .addField(
                "Reserve",
                discordIdsAsMentions(eventGroup.getReserveIds()),
                false
                )

                .addField(
                        "Owner",
                        String.format("<@%s>", eventGroup.getOwnerId()),
                        false
                )
                .setTimestamp(eventGroup.getLastActivity());

        return this;
    }

    public String discordIdsAsMentions(List<String> discorIds) {

        if (discorIds.isEmpty()) return "No members";

        StringBuilder sb = new StringBuilder();
        discorIds.stream().forEach(id -> sb.append(String.format("<@%s>\n", id)));

        return sb.toString();
    }
}
