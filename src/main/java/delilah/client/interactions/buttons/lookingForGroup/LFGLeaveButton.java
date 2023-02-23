package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LFGLeaveButton extends AbstractButtonCommand {

    @Autowired
    private LookingForGroupService lfgService;

    public LFGLeaveButton() {
        super("lfg_leave");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        lfgService.leaveGroup(event.getUser().getId(), event.getMessageId());
        event.reply("Group left").setEphemeral(true).queue();
    }
}
