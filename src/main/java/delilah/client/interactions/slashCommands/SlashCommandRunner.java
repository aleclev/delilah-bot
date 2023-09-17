package delilah.client.interactions.slashCommands;

import delilah.client.interactions.slashCommands.payloadProcessing.SlashCommandPayloadExtractor;
import delilah.domain.exceptions.DelilahException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.lang.reflect.InvocationTargetException;

public class SlashCommandRunner implements Runnable {

    AbstractSlashCommand command;
    SlashCommandInteractionEvent commandEvent;
    SlashCommandPayloadExtractor extractor;

    public SlashCommandRunner(AbstractSlashCommand command, SlashCommandInteractionEvent commandEvent, SlashCommandPayloadExtractor extractor) {
        this.command = command;
        this.commandEvent = commandEvent;
        this.extractor = extractor;
    }

    @Override
    public void run() {

        try {

            Object payload = extractor.extractPayload(command.getClass(), commandEvent.getOptions());

            command.getClass().getMethod("execute", SlashCommandInteractionEvent.class, Object.class).invoke(command, commandEvent, payload);

        } catch (DelilahException e) {
            notifyUserOfException(e.getMessage());
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof DelilahException)
                notifyUserOfException(e.getTargetException().getMessage());
            else
                notifyUserOfException("An unknown error occurred !");
            e.printStackTrace();
        } catch (Exception e) {
            notifyUserOfException("An unknown error occurred !");
            e.printStackTrace();
        }
    }

    private void notifyUserOfException(String message) {

        if (commandEvent.isAcknowledged())
            commandEvent.getHook().sendMessage(message).setEphemeral(true).queue();
        else
            commandEvent.reply(message).setEphemeral(true).queue();
    }
}
