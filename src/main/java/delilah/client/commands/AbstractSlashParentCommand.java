package delilah.client.commands;

import delilah.client.commands.payloadProcessing.SlashCommandPayloadExtractor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class AbstractSlashParentCommand extends AbstractSlashCommand {

    private final List<AbstractSlashSubcommand> subcommands = new ArrayList<>();
    private SlashCommandPayloadExtractor payloadExtractor;
    public AbstractSlashParentCommand(@NotNull String name, @NotNull String description, SlashCommandPayloadExtractor payloadExtractor) {
        super(name, description);

        this.payloadExtractor = payloadExtractor;
    }

    protected void deferToChildren(SlashCommandInteractionEvent commandEvent) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ExecutionException, InterruptedException {

        String commandName = commandEvent.getInteraction().getSubcommandName();
        AbstractSlashSubcommand comman = findChildCommand(commandName);

        Object payload = payloadExtractor.extractPayload(comman.getClass(), commandEvent.getOptions());

        comman.execute(commandEvent, payload);
    }

    @Nullable
    private AbstractSlashSubcommand findChildCommand(String commandName) {
        return this.subcommands.stream().filter(command -> (command).getName().equals(commandName)).findFirst().orElse(null);
    }

    protected void registerSubcommand(AbstractSlashSubcommand slashSubcommand) {
        subcommands.add(slashSubcommand);
        addSubcommands(slashSubcommand);
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        try {
            deferToChildren(commandEvent);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        String commandName = event.getSubcommandName();
        findChildCommand(commandName).onCommandAutoCompleteInteraction(event);
    }
}
