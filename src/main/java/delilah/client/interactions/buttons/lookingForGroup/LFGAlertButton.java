package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.commands.notification.NotificationBroadcastCommand;
import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class LFGAlertButton extends AbstractButtonCommand {

    @Autowired
    private LookingForGroupService lfgService;

    @Autowired
    private NotificationBroadcastCommand notificationService;

    public LFGAlertButton() {
        super("lfg_alert");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) throws ExecutionException, InterruptedException {

        var report = lfgService.sendAlert(event.getUser().getId(), event.getMessageId(), event.getMessage().getJumpUrl());

        event.reply(report.toString()).setEphemeral(true).queue();
    }
}
