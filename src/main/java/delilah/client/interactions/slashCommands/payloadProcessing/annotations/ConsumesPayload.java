package delilah.client.interactions.slashCommands.payloadProcessing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumesPayload {
    Class<?> type();
}
