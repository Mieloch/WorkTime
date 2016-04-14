package topworker.dal.validation;

import topworker.dal.entity.EWorkPeriod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by echomil on 13.04.16.
 */
public class StartBeforeStopValidator implements ConstraintValidator<ValidStartBeforeStop, EWorkPeriod> {


    @Override
    public void initialize(ValidStartBeforeStop constraintAnnotation) {

    }

    @Override
    public boolean isValid(EWorkPeriod value, ConstraintValidatorContext context) {

        Date start = value.getStart();
        Date stop = value.getStop();
        if (start == null || stop == null) {
            return false;
        }
        return start.before(stop);
    }
}
