package topworker.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * Created by echomil on 14.04.16.
 */
@StaticMetamodel(WorkDay.class)

public class WorkDay_ {
    public static volatile SingularAttribute<WorkDay, Date> date;
    public static volatile SingularAttribute<WorkDay, User> user;
    public static volatile SingularAttribute<WorkDay, Integer> id;

}
