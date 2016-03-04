package topworker.model.dal.entity.metadata;

import topworker.model.dal.entity.EUserDetails;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EUserDetails.class)
public class EUserDetails_ {
	public static volatile SingularAttribute<EUserDetails, Long> id;
	public static volatile SingularAttribute<EUserDetails, String> name;
	public static volatile SingularAttribute<EUserDetails, String> lastName;
}
