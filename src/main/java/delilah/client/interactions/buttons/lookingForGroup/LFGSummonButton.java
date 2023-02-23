package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LFGSummonButton extends AbstractButtonCommand {

    @Autowired
    private LookingForGroupService lfgService;

    public LFGSummonButton() {
        super("lfg_summon");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        event.deferReply(true).queue();
        lfgService.summonGroup(event.getUser().getId(), event.getMessageId());

        event.getHook().sendMessage("Members summoned.").setEphemeral(true).queue();
    }
}
