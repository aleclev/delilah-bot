package delilah.client.interactions.buttons;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.concurrent.ExecutionException;

@Getter
public abstract class AbstractButtonCommand {

    protected String id;

    public AbstractButtonCommand(String id) {
        this.id = id;
    }

    public abstract void onButtonInteraction(ButtonInteractionEvent event) throws ExecutionException, InterruptedException;
}
