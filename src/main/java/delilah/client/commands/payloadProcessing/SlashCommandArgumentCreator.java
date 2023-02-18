package delilah.client.commands.payloadProcessing;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.user.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class SlashCommandArgumentCreator {

    public void populateOptionsMapping(AbstractSlashCommand command) {

        Class commandClass = command.getClass();
        ConsumesPayload expectedAnnotation = (ConsumesPayload) commandClass.getAnnotation(ConsumesPayload.class);

        if (expectedAnnotation == null) return;

        Class payloadClass = expectedAnnotation.type();

        for (Field field: payloadClass.getFields()) {
            if (field.isAnnotationPresent(Argument.class)) {
                Argument argument = field.getAnnotation(Argument.class);

                command.addOption(
                        getOptionTypeFromFieldType(field.getType()),
                        argument.name().equals("") ? field.getName() : argument.name(),
                        argument.description(),
                        !argument.optional(),
                        argument.autocomplete()
                );
            }
        }
    }

    private OptionType getOptionTypeFromFieldType(Class fieldType) {
        if (fieldType.equals(String.class)) return OptionType.STRING;
        else if (fieldType.equals(Integer.class)) return OptionType.NUMBER;
        else if (fieldType.equals(User.class)) return OptionType.USER;
        else throw new IllegalArgumentException(String.format("No suitable OptionType for class %s", fieldType.getName()));
    }
}
