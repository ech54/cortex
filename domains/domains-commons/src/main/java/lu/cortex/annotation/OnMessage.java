package lu.cortex.annotation;

import java.lang.annotation.*;

/**
 * Created by Emilien Charton on 10/01/16.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnMessage {

    String name() default "";

}
