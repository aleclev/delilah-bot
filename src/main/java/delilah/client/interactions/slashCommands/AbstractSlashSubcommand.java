package delilah.client.interactions.slashCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public abstract class AbstractSlashSubcommand extends SubcommandData {

    public AbstractSlashSubcommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    public abstract void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException;

    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {}
}
