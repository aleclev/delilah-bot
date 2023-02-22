package delilah.client.interactions.buttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ButtonEventHandler {

    @Autowired
    private List<AbstractButtonCommand> buttonCommands;

    public void onButtonInteraction(ButtonInteractionEvent event) {
        getButton(event.getButton().getId()).onButtonInteraction(event);
    }

    private AbstractButtonCommand getButton(String buttonId) {
        return buttonCommands.stream().filter(c -> c.getId().equals(buttonId)).findFirst().orElse(null);
    }
}
