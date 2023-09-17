package delilah.client.interactions.buttons;

import delilah.domain.exceptions.DelilahException;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ButtonEventHandler {

    @Autowired
    private List<AbstractButtonCommand> buttonCommands;

    public void onButtonInteraction(ButtonInteractionEvent event) {

        try {
            event.deferReply(true).queue();
            getButton(event.getButton().getId()).onButtonInteraction(event);
        } catch (DelilahException e) {
            notifyUserOfException(e.getMessage(), event);
        } catch (Exception e) {
            notifyUserOfException("An unknown error occurred!", event);
        }
    }

    private void notifyUserOfException(String message, ButtonInteractionEvent event) {

        if (event.isAcknowledged())
            event.getHook().sendMessage(message).setEphemeral(true).queue();
        else
            event.reply(message).setEphemeral(true).queue();
    }

    private AbstractButtonCommand getButton(String buttonId) {
        return buttonCommands.stream().filter(c -> c.getId().equals(buttonId)).findFirst().orElse(null);
    }
}
