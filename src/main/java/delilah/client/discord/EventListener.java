package delilah.client.discord;

import delilah.client.commands.CommandEventHandler;
import delilah.client.interactions.buttons.ButtonEventHandler;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener extends ListenerAdapter {

    @Autowired
    private CommandEventHandler dispatcher;

    @Autowired
    private ButtonEventHandler buttonEventHandler;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        dispatcher.onSlashcommandInteractionEvent(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        dispatcher.onCommandAutoCompleteInteraction(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        buttonEventHandler.onButtonInteraction(event);
    }
}
