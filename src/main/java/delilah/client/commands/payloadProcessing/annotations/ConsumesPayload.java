package delilah.client.commands.payloadProcessing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumesPayload {
    Class<?> type();
}
