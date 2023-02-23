package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LFGJoinAlternateButton extends AbstractButtonCommand {

    @Autowired
    private LookingForGroupService lfgService;

    public LFGJoinAlternateButton() {
        super("lfg_join_alternate");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        lfgService.joinGroupAlternate(event.getUser().getId(), event.getMessageId());
        event.reply("Group joined as alternate.").setEphemeral(true).queue();
    }
}
