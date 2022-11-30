package delilah.interf4ce.commands;

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSlashCommand extends CommandDataImpl {

    public AbstractSlashCommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    public abstract void execute(SlashCommandInteractionEvent commandEvent, Object payload);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CommandData) return equals((CommandData)obj);

        return super.equals(obj);
    }

    public boolean equals(SlashCommandData command) {
        if (!command.getName().equals(getName())) return false;
        if (!command.getDescription().equals(getDescription())) return false;
        if (command.getOptions().size() != getOptions().size()) return false;
        if (!command.getOptions().equals(getOptions())) return false;

        return true;
    }

    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

    }
}
