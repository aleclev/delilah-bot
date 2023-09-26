package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.lookingForGroup.EventGroupPruningService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class LFGDeleteButton extends AbstractButtonCommand {

    private final EventGroupPruningService eventGroupPruningService;

    public LFGDeleteButton(EventGroupPruningService eventGroupPruningService) {
        super("lfg_delete");
        this.eventGroupPruningService = eventGroupPruningService;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) throws ExecutionException, InterruptedException {

        event.deferReply(true).queue();

        String userId = event.getUser().getId();
        String groupId = event.getMessageId();
        eventGroupPruningService.deleteGroupAs(userId, groupId);

        event.getHook().sendMessage("Group deleted!").setEphemeral(true).queue();
    }
}
