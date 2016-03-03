package topworker.model.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EUserDetails.class)
public class EUser_ {
	public static volatile SingularAttribute<EUserDetails, Long> id;
	public static volatile SingularAttribute<EUserDetails, String> name;
	public static volatile SingularAttribute<EUserDetails, String> lastName;
}
