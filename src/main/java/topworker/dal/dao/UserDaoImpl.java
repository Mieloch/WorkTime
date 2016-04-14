package topworker.dal.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.UserDao;
import topworker.dal.entity.User;
import topworker.dal.entity.UserRoles;
import topworker.dal.entity.UserRoles_;
import topworker.dal.entity.User_;
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
    public User findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(root.get(User_.login).in(login));
        Query query = entityManager.createQuery(cq);
        return (User) getSingleResultFromQuery(query);

    }

    @Override
    public List<User> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public User findById(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.where(root.get(User_.id).in(id));
        Query query = entityManager.createQuery(cq);

        return (User) getSingleResultFromQuery(query);

    }

    @Override
    public void persist(User entity) {
        entityManager.persist(entity);
    }

    public Set<UserRoles> getRoles(Set<UserRole> roles) {
        List<String> rolesTypes = new ArrayList<>();
        for (UserRole role : roles) {
            rolesTypes.add(role.getType());
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserRoles> cq = cb.createQuery(UserRoles.class);
        Root<UserRoles> root = cq.from(UserRoles.class);
        cq.where(root.get(UserRoles_.type).in(rolesTypes));
        Query query = entityManager.createQuery(cq);
        Set<UserRoles> rolesSet = new HashSet<>();
        rolesSet.addAll(query.getResultList());
        return rolesSet;
    }

    private Object getSingleResultFromQuery(Query query) {
        try {
            Object o = query.getSingleResult();
            return o;
        } catch (javax.persistence.PersistenceException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
