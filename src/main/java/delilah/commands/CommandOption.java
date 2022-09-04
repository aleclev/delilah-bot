package delilah.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class CommandOption extends OptionData {
    public CommandOption(@NotNull OptionType type, @NotNull String name, @NotNull String description) {
        super(type, name, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OptionData) return equals((OptionData)obj);

        return super.equals(obj);
    }

    public boolean equals(OptionData option) {
        if (!option.getName().equals(getName())) return false;
        if (!option.getDescription().equals(getDescription())) return false;
        if (!option.getType().equals(getType())) return false;

        return true;
    }
}
