package delilah.interf4ce.commands.payloadProcessing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
    boolean optional() default false;
    String name() default "";
    String description() default "No description given";
}
