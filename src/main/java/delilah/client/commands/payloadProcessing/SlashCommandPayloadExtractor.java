package delilah.client.commands.payloadProcessing;

import delilah.client.commands.payloadProcessing.annotations.Argument;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.infrastructure.repositories.UserRepository;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.List;

@Component
public class SlashCommandPayloadExtractor {

    @Autowired
    UserRepository userRepository;

    public Object extractPayload(Class commandClass, Class<Object> payloadClass, SlashCommandInteractionEvent commandEvent) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        ConsumesPayload expectedAnnotation = (ConsumesPayload) commandClass.getAnnotation(ConsumesPayload.class);

        if (expectedAnnotation == null) return null;

        Object payload = payloadClass.getConstructor().newInstance();

        for (Field field: payloadClass.getFields()) {

            String argumentName = getArgumentNameOfField(field);

            Optional<OptionMapping> mapping = getOption(commandEvent.getOptions(), argumentName);

            if (mapping.isEmpty()) continue;

            Object value = extractArgument(mapping.get());

            injectValue(field, payload, value);
        }

        return payload;
    }

    private String getArgumentNameOfField(Field field) {
        Argument argument = field.getAnnotation(Argument.class);

        return argument.name().equals("") ? field.getName() : argument.name();
    }

    private void injectValue(Field field, Object payload, Object value) throws IllegalAccessException {

        if (value instanceof User) value = userRepository.findByDiscordId(((User) value).getId());

        field.set(payload, value);
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

            case USER:
                return mapping.getAsUser();
        }
        return null;
    }
}
