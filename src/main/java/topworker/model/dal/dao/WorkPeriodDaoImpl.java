package topworker.model.dal.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.bo.WorkPeriod;
import topworker.model.dal.entity.EWorkPeriod;
import topworker.model.dal.WorkPeriodDao;
import topworker.model.dal.entity.metadata.EWorkPeriod_;

@Repository(value = "WorkPeriodDaoImpl")
@Transactional(value = "PlatformTransactionManager")
public class WorkPeriodDaoImpl implements WorkPeriodDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Override
    public List<EWorkPeriod> getFromDateToDate(Date startDate, Date endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
        Root<EWorkPeriod> root = cq.from(EWorkPeriod.class);
        cq.where(cb.between(root.get(EWorkPeriod_.start), startDate, endDate));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void postTime(WorkPeriod timeStamp) {
        EWorkPeriod e = getByStartDate(timeStamp.getStart());
        if (e == null) {
            e = new EWorkPeriod();
            e.setStart(timeStamp.getStart());
        }
        e.setStop(timeStamp.getStop());

        e.setUserDetails(userDetailsDao.getUserById(1l));
        entityManager.persist(e);
        // entityManager.merge(e);
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
        } catch (NoResultException e) {
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

}
