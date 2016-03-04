package topworker.model.dal.entity;

import topworker.model.dal.entity.EUser;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EUser.class)
public class EUser_ {
    public static volatile SingularAttribute<EUser, String> login;
    public static volatile SingularAttribute<EUser, Integer> id;
}