package delilah.client.commands.payloadProcessing;

import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.models.user.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlashCommandArgumentCreator {

    public List<OptionData> extractOptions(Class commandClass) {

        ConsumesPayload expectedAnnotation = (ConsumesPayload) commandClass.getAnnotation(ConsumesPayload.class);

        if (expectedAnnotation == null) return new ArrayList<>();

        Class payloadClass = expectedAnnotation.type();

        List<OptionData> options = new ArrayList<>();

        for (Field field: payloadClass.getFields()) {
            if (field.isAnnotationPresent(Argument.class)) {
                Argument argument = field.getAnnotation(Argument.class);

                OptionData optionData = new OptionData(
                        getOptionTypeFromFieldType(field.getType()),
                        argument.name().equals("") ? field.getName() : argument.name(),
                        argument.description()
                )
                .setRequired(!argument.optional())
                .setAutoComplete(argument.autocomplete());

                options.add(optionData);
            }
        }

        return options;
    }

    private OptionType getOptionTypeFromFieldType(Class fieldType) {
        if (fieldType.equals(String.class)) return OptionType.STRING;
        else if (fieldType.equals(Integer.class)) return OptionType.INTEGER;
        else if (fieldType.equals(User.class)) return OptionType.USER;
        else throw new IllegalArgumentException(String.format("No suitable OptionType for class %s", fieldType.getName()));
    }
}
