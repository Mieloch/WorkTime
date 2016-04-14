package topworker.dal.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by echomil on 13.04.16.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StartBeforeStopValidator.class})
public @interface ValidStartBeforeStop {
    String message() default "startDate must be before stopDate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
