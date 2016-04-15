package topworker.dal.dao;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.WorkDayDao;
import topworker.dal.entity.User;
import topworker.dal.entity.User_;
import topworker.dal.entity.WorkDay;
import topworker.dal.entity.WorkDay_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by echomil on 14.04.16.
 */
@Repository(value = "WorkDayDaoImpl")
@Transactional(value = "PlatformTransactionManager")
public class WorkDayDaoImpl implements WorkDayDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public WorkDay getByDateAndUser(String userName, Date date) {
        date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkDay> cq = cb.createQuery(WorkDay.class);
        Root<WorkDay> root = cq.from(WorkDay.class);
        Join<WorkDay, User> user = root.join(WorkDay_.user);
        cq.where(cb.and(root.get(WorkDay_.date).in(date), user.get(User_.login).in(userName)));
        Query q = entityManager.createQuery(cq);
        return (WorkDay) getSingleResultFromQuery(q);
    }

    @Override
    public void create(WorkDay workDay) {
        entityManager.persist(workDay);
    }

    @Override
    public List<WorkDay> getBetweenDates(Date start, Date end, String username) {
        start = DateUtils.truncate(start, Calendar.DAY_OF_MONTH);
        end = DateUtils.truncate(end, Calendar.DAY_OF_MONTH);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkDay> cq = cb.createQuery(WorkDay.class);
        Root<WorkDay> root = cq.from(WorkDay.class);
        Join<WorkDay, User> user = root.join(WorkDay_.user);
        cq.where(cb.and(user.get(User_.login).in(username), cb.between(root.get(WorkDay_.date), start, end)));
        Query q = entityManager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void removeAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkDay> cq = cb.createQuery(WorkDay.class);
        Root<WorkDay> root = cq.from(WorkDay.class);
        Query q = entityManager.createQuery(cq);
        List<WorkDay> workDays = q.getResultList();
        for (WorkDay workDay : workDays) {
            entityManager.remove(workDay);
        }
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
