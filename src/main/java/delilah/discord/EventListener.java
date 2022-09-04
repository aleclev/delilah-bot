package delilah.discord;

import delilah.commands.CommandDispatcher;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener extends ListenerAdapter {

    @Autowired
    private CommandDispatcher dispatcher;

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        dispatcher.handle(event);
    }
}
