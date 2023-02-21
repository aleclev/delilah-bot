package delilah.client.commands;

import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.client.commands.payloadProcessing.SlashCommandPayloadExtractor;
import delilah.domain.exceptions.AbstractDelilahException;
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

        } catch (AbstractDelilahException e) {
            commandEvent.reply(e.getMessage()).queue();
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof AbstractDelilahException)
                commandEvent.getHook().sendMessage(e.getTargetException().getMessage()).queue();
            else
                commandEvent.getHook().sendMessage("An unknown error occurred !").queue();
            e.printStackTrace();
        } catch (Exception e) {
            commandEvent.getHook().sendMessage("An unknown error occurred !").queue();
            e.printStackTrace();
        }
    }
}
