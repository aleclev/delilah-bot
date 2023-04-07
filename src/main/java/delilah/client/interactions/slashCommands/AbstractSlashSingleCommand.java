package delilah.client.interactions.slashCommands;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractSlashSingleCommand extends AbstractSlashCommand {
    public AbstractSlashSingleCommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }
}
