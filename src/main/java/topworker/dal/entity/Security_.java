package topworker.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by echomil on 22.03.16.
 */
@StaticMetamodel(value = Security.class)
public class Security_ {
    public static volatile SingularAttribute<Security, String> name;
    public static volatile SingularAttribute<Security, Integer> id;
}
