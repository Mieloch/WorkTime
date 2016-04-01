package topworker.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by echomil on 22.03.16.
 */
@StaticMetamodel(value = ESecurity.class)
public class ESecurity_ {
    public static volatile SingularAttribute<ESecurity, String> name;
    public static volatile SingularAttribute<ESecurity, Integer> id;
}
