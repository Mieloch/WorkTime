package topworker.dal.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.UserDao;
import topworker.dal.WorkPeriodDao;
import topworker.dal.entity.*;

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
    public void persist(WorkPeriod period) {
        entityManager.persist(period);
    }

    @Override
    public List<WorkPeriod> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> c = cq.from(WorkPeriod.class);
        cq.orderBy(cb.asc(c.get(WorkPeriod_.start)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }


    @Override
    public WorkPeriod getByStartDate(Date startDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> period = cq.from(WorkPeriod.class);
        cq.where(period.get(WorkPeriod_.start).in(startDate));
        Query q = entityManager.createQuery(cq);
        try {
            return (WorkPeriod) q.getSingleResult();
        } catch (javax.persistence.PersistenceException e) {
            return null;
        }

    }

    @Override
    public List<WorkPeriod> getAllStartingIn(Date start) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> root = cq.from(WorkPeriod.class);
        cq.where(root.get(WorkPeriod_.start).in(start));
        Query q = entityManager.createQuery(cq);

        return q.getResultList();
    }

    @Override
    public WorkPeriod findLastPeriodInStreak(WorkDay workDay, WorkPeriod period) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> root = cq.from(WorkPeriod.class);
        Join<WorkPeriod, WorkDay> workDayJoin = root.join(WorkPeriod_.workDay);
        cq.where(cb.and(workDayJoin.get(WorkDay_.id).in(workDay.getId()), root.get(WorkPeriod_.stop).in(period.getStart())));
        Query q = entityManager.createQuery(cq);
        return (WorkPeriod) getSingleResultFromQuery(q);

    }
    @Override
    public List<WorkPeriod> getAllBelongToUser(String user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> root = cq.from(WorkPeriod.class);
        Join<WorkPeriod, WorkDay> workDayJoin = root.join(WorkPeriod_.workDay);
        Join<WorkDay, User> userJoin = workDayJoin.join(WorkDay_.user);
        cq.where(userJoin.get(User_.login).in(user));
        cq.orderBy(cb.desc(root.get(WorkPeriod_.start)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void removeAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> root = cq.from(WorkPeriod.class);
        Query q = entityManager.createQuery(cq);
        List<WorkPeriod> workPeriods = q.getResultList();
        for (WorkPeriod workPeriod : workPeriods) {
            entityManager.remove(workPeriod);
        }
    }

    @Override
    public WorkPeriod get(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkPeriod> cq = cb.createQuery(WorkPeriod.class);
        Root<WorkPeriod> root = cq.from(WorkPeriod.class);
        cq.where(root.get(WorkPeriod_.id).in(id));
        Query q = entityManager.createQuery(cq);
        return (WorkPeriod) getSingleResultFromQuery(q);
    }

    @Override
    public boolean exists(long id) {
        return (get(id) == null);
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
