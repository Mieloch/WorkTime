package topworker.model.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(EWorkPeriod.class)
public class EWorkPeriod_ {
	public static volatile SingularAttribute<EWorkPeriod, Date> start;
	public static volatile SingularAttribute<EWorkPeriod, Date> stop;
	public static volatile SingularAttribute<EWorkPeriod, EUser> user;


}
