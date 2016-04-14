package topworker.dal.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.UserDao;
import topworker.dal.WorkPeriodDao;
import topworker.dal.entity.EUser;
import topworker.dal.entity.EUser_;
import topworker.dal.entity.EWorkPeriod;
import topworker.dal.entity.EWorkPeriod_;
import topworker.model.bo.WorkPeriod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository(value = "WorkPeriodDaoImpl")
@Transactional(value = "PlatformTransactionManager")
public class WorkPeriodDaoImpl implements WorkPeriodDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    @Override
    public List<EWorkPeriod> getFromDateToDate(Date start, Date stop, String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> root = cq.from(EWorkPeriod.class);
        Join<EWorkPeriod, EUser> join = root.join(EWorkPeriod_.user);
        cq.where(cb.and(cb.between(root.get(EWorkPeriod_.start), start, stop), join.get(EUser_.login).in(login)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void persist(EWorkPeriod period) {
        entityManager.persist(period);
    }

    @Override
    public List<EWorkPeriod> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> c = cq.from(EWorkPeriod.class);
        cq.orderBy(cb.asc(c.get(EWorkPeriod_.start)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }


    @Override
    public EWorkPeriod getByStartDate(Date startDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> period = cq.from(EWorkPeriod.class);
        cq.where(period.get(EWorkPeriod_.start).in(startDate));
        Query q = entityManager.createQuery(cq);
        try {
            return (EWorkPeriod) q.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            return null;
        }

    }

    @Override
    public List<EWorkPeriod> getAllStartingIn(Date start) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> root = cq.from(EWorkPeriod.class);
        cq.where(root.get(EWorkPeriod_.start).in(start));
        Query q = entityManager.createQuery(cq);

        return q.getResultList();
    }

    @Override
    public EWorkPeriod postTimeToUser(String user, WorkPeriod period) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> root = cq.from(EWorkPeriod.class);
        Join<EWorkPeriod, EUser> userJoin = root.join(EWorkPeriod_.user);
        cq.where(cb.and(userJoin.get(EUser_.login).in(user), root.get(EWorkPeriod_.stop).in(period.getStart())));
        Query q = entityManager.createQuery(cq);
        return (EWorkPeriod) getSingleResultFromQuery(q);

    }
    @Override
    public List<EWorkPeriod> getAllBelongToUser(String user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> root = cq.from(EWorkPeriod.class);
        Join<EWorkPeriod, EUser> userJoin = root.join(EWorkPeriod_.user);
        cq.where(userJoin.get(EUser_.login).in(user));
        cq.orderBy(cb.desc(root.get(EWorkPeriod_.start)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }

    private Object getSingleResultFromQuery(Query query) {
        try {
            return query.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
