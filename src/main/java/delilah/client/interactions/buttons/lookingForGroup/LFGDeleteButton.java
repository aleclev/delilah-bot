package delilah.client.interactions.buttons.lookingForGroup;

import delilah.client.interactions.buttons.AbstractButtonCommand;
import delilah.services.groupEvent.GroupEventPruningService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class LFGDeleteButton extends AbstractButtonCommand {

    private final GroupEventPruningService groupEventPruningService;

    public LFGDeleteButton(GroupEventPruningService groupEventPruningService) {
        super("lfg_delete");
        this.groupEventPruningService = groupEventPruningService;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) throws ExecutionException, InterruptedException {

        event.deferReply(true).queue();

        String userId = event.getUser().getId();
        String groupId = event.getMessageId();
        groupEventPruningService.deleteGroupAs(userId, groupId);

        event.getHook().sendMessage("Group deleted!").setEphemeral(true).queue();
    }
}
