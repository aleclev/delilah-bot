package delilah.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSlashCommand extends CommandData {

    protected List<String> names = new ArrayList<>();

    public AbstractSlashCommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    protected abstract void execute(SlashCommandEvent commandEvent);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CommandData) return equals((CommandData)obj);

        return super.equals(obj);
    }

    public boolean equals(CommandData command) {
        if (!command.getName().equals(getName())) return false;
        if (!command.getDescription().equals(getDescription())) return false;
        if (command.getOptions().size() != getOptions().size()) return false;
        if (!command.getOptions().equals(getOptions())) return false;

        return true;
    }
}
