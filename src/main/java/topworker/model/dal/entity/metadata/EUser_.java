package topworker.model.dal.entity.metadata;

import topworker.model.dal.entity.EUser;
import topworker.model.dal.entity.EWorkPeriod;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * Created by echomil on 04.03.16.
 */

@StaticMetamodel(EUser.class)
public class EUser_ {
    public static volatile SingularAttribute<EUser, String> login;
    public static volatile SingularAttribute<EUser, Integer> id;
}
