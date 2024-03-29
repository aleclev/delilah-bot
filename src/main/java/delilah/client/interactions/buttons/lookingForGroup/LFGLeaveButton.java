package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.groupEvent.GroupEventService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LFGLeaveButton extends AbstractButtonCommand {

    @Autowired
    private GroupEventService lfgService;

    public LFGLeaveButton() {
        super("lfg_leave");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        lfgService.leaveGroup(event.getUser().getId(), event.getMessageId());
        event.getHook().sendMessage("Group left").setEphemeral(true).queue();
    }
}
