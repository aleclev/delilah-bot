package delilah.interf4ce.commands.payloadProcessing;

import delilah.interf4ce.commands.payloadProcessing.annotations.Argument;
import delilah.interf4ce.commands.payloadProcessing.annotations.ConsumesPayload;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.List;

@Component
public class SlashCommandPayloadExtractor {

    public Object extractPayload(Class commandClass, Class<Object> payloadClass, SlashCommandInteractionEvent commandEvent) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        ConsumesPayload expectedAnnotation = (ConsumesPayload) commandClass.getAnnotation(ConsumesPayload.class);

        if (expectedAnnotation == null) return null;

        Object payload = payloadClass.getConstructor().newInstance();

        for (Field field: payloadClass.getFields()) {

            String argumentName = getArgumentNameOfField(field);

            Optional<OptionMapping> mapping = getOption(commandEvent.getOptions(), argumentName);

            if (mapping.isEmpty()) continue;

            Object value = extractArgument(mapping.get());

            field.set(payload, value);
        }

        return payload;
    }

    private String getArgumentNameOfField(Field field) {
        Argument argument = field.getAnnotation(Argument.class);

        return argument.name().equals("") ? field.getName() : argument.name();
    }
    private Optional<OptionMapping> getOption(List<OptionMapping> mappingList, String option) {
        return mappingList.stream().filter(op -> op.getName().equals(option)).findFirst();
    }

    public Object extractArgument(OptionMapping mapping) {

        switch (mapping.getType()) {
            case STRING:
                return mapping.getAsString();

            case INTEGER:
                return mapping.getAsInt();
        }
        return null;
    }
}
