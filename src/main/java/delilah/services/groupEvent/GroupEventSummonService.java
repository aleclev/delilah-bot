package delilah.services.groupEvent;

import delilah.domain.models.groupEvent.GroupEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class GroupEventSummonService {

    @Autowired
    private JDA jda;

    @Value("${delilah.discord.test-server.id}")
    private String testGuildId;

    public void summonGroup(GroupEvent group, String messageUrl) {

        String vcUrl = null;

        try {
            vcUrl = jda.getGuildById(testGuildId).retrieveMemberById(group.getOwnerId()).submit().get().getVoiceState().getChannel().getJumpUrl();
        } catch (Exception ignored) {}

        String finalVcUrl = vcUrl;
        group.getParticipantsIds().stream().filter(id -> !id.equals(group.getOwnerId())).forEach(id ->
                sendSummonMessage(id, group.getOwnerId(), group.getActivity().getLongName(), messageUrl, finalVcUrl));

        if (group.getParticipantsIds().size() < group.getMaxSize()) {
            group.getReserveIds().stream().filter(id ->
                    !id.equals(group.getOwnerId())).forEach(id -> sendSummonMessage(id, group.getOwnerId(), group.getActivity().getLongName(), messageUrl, finalVcUrl));
        }
    }

    private void sendSummonMessage(String discordId, String ownerId, String title, String messageUrl, String voiceChannelUrl) {
        try {
            jda.retrieveUserById(discordId).submit().get().openPrivateChannel().flatMap(
                    c -> c.sendMessage(String.format("<@%s> is summoning you to: %s", ownerId, title)).addActionRow(
                            List.of(
                                    Button.link(messageUrl, "Jump to message"),
                                    Objects.isNull(voiceChannelUrl) ? Button.secondary("owner_not_in_vc", "Owner not in VC"): Button.link(voiceChannelUrl, "Jump to VC")
                            )
                    )
            ).queue();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
