package topworker.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(WorkPeriod.class)
public class WorkPeriod_ {
    public static volatile SingularAttribute<WorkPeriod, Date> start;
    public static volatile SingularAttribute<WorkPeriod, Date> stop;
    public static volatile SingularAttribute<WorkPeriod, WorkDay> workDay;
}
