package topworker.dal.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.UserDao;
import topworker.dal.entity.EUser;
import topworker.dal.entity.EUserRoles;
import topworker.dal.entity.EUserRoles_;
import topworker.dal.entity.EUser_;
import topworker.model.bo.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */

@Repository(value = "UserDaoImpl")
@Transactional(value = "PlatformTransactionManager", propagation = Propagation.REQUIRED)
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EUser findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUser> cq = cb.createQuery(EUser.class);
        Root<EUser> root = cq.from(EUser.class);
        cq.where(root.get(EUser_.login).in(login));
        Query query = entityManager.createQuery(cq);
        return (EUser) query.getSingleResult();
    }

    @Override
    public List<EUser> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUser> cq = cb.createQuery(EUser.class);
        Root<EUser> root = cq.from(EUser.class);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public EUser findById(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUser> cq = cb.createQuery(EUser.class);
        Root<EUser> root = cq.from(EUser.class);
        cq.where(root.get(EUser_.id).in(id));
        Query query = entityManager.createQuery(cq);
        return (EUser) query.getSingleResult();
    }

    @Override
    public void persist(EUser entity) {
        entityManager.persist(entity);
    }

    public Set<EUserRoles> getRoles(Set<UserRole> roles) {
        List<String> rolesTypes = new ArrayList<>();
        for (UserRole role : roles) {
            rolesTypes.add(role.getType());
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUserRoles> cq = cb.createQuery(EUserRoles.class);
        Root<EUserRoles> root = cq.from(EUserRoles.class);
        cq.where(root.get(EUserRoles_.type).in(rolesTypes));
        Query query = entityManager.createQuery(cq);
        Set<EUserRoles> rolesSet = new HashSet<>();
        rolesSet.addAll(query.getResultList());
        return rolesSet;
    }


}
