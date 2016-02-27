package topworker.model.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EUser.class)
public class EUser_ {
	public static volatile SingularAttribute<EUser, Long> id;
	public static volatile SingularAttribute<EUser, String> name;
	public static volatile SingularAttribute<EUser, String> lastName;
}
