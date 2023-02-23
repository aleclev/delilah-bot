package delilah.client.commands.notification;

import delilah.client.commands.AbstractSlashSubcommand;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class NotificationListCommand extends AbstractSlashSubcommand {

    @Autowired
    UserRepository userRepository;

    public NotificationListCommand() {
        super("list", "Show a list of your registered tags");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        User user = userRepository.findById(commandEvent.getUser().getId());

        if (user.getNotificationProfile().getSubscriptions().isEmpty()) {
            commandEvent.reply("No subscriptions.").queue();
            return;
        }

        StringBuilder sb = new StringBuilder("```");

        user.getNotificationProfile().getSubscriptions().stream().forEach(s -> sb.append(s.getTag()).append("\n"));

        sb.append("```");

        commandEvent.reply(sb.toString()).queue();
    }
}
