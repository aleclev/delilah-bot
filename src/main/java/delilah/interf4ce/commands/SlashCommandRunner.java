package delilah.interf4ce.commands;

import delilah.interf4ce.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.interf4ce.commands.payloadProcessing.SlashCommandPayloadExtractor;
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
            Object payload = null;
            if (command.getClass().isAnnotationPresent(ConsumesPayload.class)) {
                Class payloadClass = command.getClass().getAnnotation(ConsumesPayload.class).type();
                payload = extractor.extractPayload(command.getClass(), payloadClass, commandEvent);
            }

            command.getClass().getMethod("execute", SlashCommandInteractionEvent.class, Object.class).invoke(command, commandEvent, payload);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
