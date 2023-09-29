package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.groupEvent.GroupEventService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LFGJoinButton extends AbstractButtonCommand {

    @Autowired
    private GroupEventService lfgService;

    public LFGJoinButton() {
        super("lfg_join");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        lfgService.joinGroup(event.getUser().getId(), event.getMessageId());
        event.getHook().sendMessage("Group joined.").setEphemeral(true).queue();
    }
}
