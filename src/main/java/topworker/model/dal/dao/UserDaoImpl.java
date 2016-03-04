package topworker.model.dal.dao;

import org.springframework.beans.factory.annotation.Autowired;
import topworker.model.dal.UserDao;
import topworker.model.dal.entity.EUser;
import topworker.model.dal.entity.metadata.EUser_;
import topworker.model.dal.entity.metadata.EWorkPeriod_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by echomil on 04.03.16.
 */
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EUser findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUser> cq =cb.createQuery(EUser.class);
        Root<EUser> root = cq.from(EUser.class);
        cq.where(root.get(EUser_.login).in(login));
        Query query = entityManager.createQuery(cq);
        return (EUser) query.getSingleResult();
    }

    @Override
    public EUser findById(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EUser> cq =cb.createQuery(EUser.class);
        Root<EUser> root = cq.from(EUser.class);
        cq.where(root.get(EUser_.id).in(id));
        Query query = entityManager.createQuery(cq);
        return (EUser) query.getSingleResult();
    }
}
