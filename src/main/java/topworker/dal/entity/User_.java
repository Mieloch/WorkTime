package topworker.dal.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SetAttribute<User, WorkPeriod> workPeriods;

}