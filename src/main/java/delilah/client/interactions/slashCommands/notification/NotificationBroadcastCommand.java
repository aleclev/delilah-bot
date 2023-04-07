package delilah.client.interactions.slashCommands.notification;

import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.commandPayloads.NotificationBroadcastCommandPayload;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.notification.NotificationBroadcastReport;
import delilah.services.NotificationBroadcastService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConsumesPayload(type = NotificationBroadcastCommandPayload.class)
public class NotificationBroadcastCommand extends AbstractSlashSubcommand {

    @Autowired
    NotificationBroadcastService notificationBroadcastService;

    @Autowired
    Clock clock;

    public NotificationBroadcastCommand() {
        super("ping", "Send a notification to users who have subscribed to some tags.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        NotificationBroadcastCommandPayload p = (NotificationBroadcastCommandPayload)payload;
        String discordId = commandEvent.getUser().getId();
        Guild guild = commandEvent.getGuild();

        commandEvent.deferReply().queue();

        StringBuilder sb = new StringBuilder();
        Stream.of(p.tag1, p.tag2, p.tag3).forEach(sub -> sb.append("#").append(sub).append(" "));
        sb.append(": ");

        //String threadUrl = createThreadAndGetUrl(threadContainer, sb.toString());
        String url = commandEvent.getChannel().sendMessage(sb + p.message).submit().get().getJumpUrl();

        NotificationBroadcastReport report = notificationBroadcastService
                .broadCastToTagsAsUser(
                        Stream.of(p.tag1, p.tag2, p.tag3).filter(Objects::nonNull).collect(Collectors.toList()),
                        p.message,
                        discordId,
                        guild,
                        clock,
                        url);
                        //commandEvent.getChannel().asThreadContainer());

        commandEvent.getHook().sendMessage((String.format("%s", report))).queue();
    }
}
