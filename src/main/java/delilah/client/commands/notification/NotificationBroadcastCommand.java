package delilah.client.commands.notification;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.NotificationBroadcastCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.notification.NotificationBroadcastReport;
import delilah.domain.models.user.User;
import delilah.services.NotificationBroadcastService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConsumesPayload(type = NotificationBroadcastCommandPayload.class)
public class NotificationBroadcastCommand extends AbstractSlashCommand {

    @Autowired
    NotificationBroadcastService notificationBroadcastService;

    @Autowired
    Clock clock;

    public NotificationBroadcastCommand() {
        super("notification-broadcast", "Send a notification to users who have subscribed to some tags.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        NotificationBroadcastCommandPayload p = (NotificationBroadcastCommandPayload)payload;
        String discordId = commandEvent.getUser().getId();
        Guild guild = commandEvent.getGuild();

        commandEvent.deferReply().queue();

        NotificationBroadcastReport report = notificationBroadcastService
                .broadCastToTagsAsUser(
                        Stream.of(p.tag1, p.tag2, p.tag3).filter(Objects::nonNull).collect(Collectors.toList()),
                        p.message,
                        discordId,
                        guild,
                        clock,
                        commandEvent.getChannel().asThreadContainer());

        commandEvent.getHook().sendMessage((String.format("%s", report))).queue();
    }
}
