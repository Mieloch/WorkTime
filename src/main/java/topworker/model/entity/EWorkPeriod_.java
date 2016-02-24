package topworker.model.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EWorkPeriod.class)
public class EWorkPeriod_ {
	public static volatile SingularAttribute<EWorkPeriod, Date> start;
	public static volatile SingularAttribute<EWorkPeriod, Date> stop;

}
