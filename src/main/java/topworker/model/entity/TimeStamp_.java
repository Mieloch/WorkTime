package topworker.model.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TimeStamp.class)
public class TimeStamp_ {
	public static volatile SingularAttribute<TimeStamp, User> user;
	public static volatile SingularAttribute<TimeStamp, Date> time;

}
