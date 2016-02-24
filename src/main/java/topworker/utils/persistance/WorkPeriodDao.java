package topworker.utils.persistance;

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

import topWorker.restClient.model.WorkPeriod;
import topworker.model.entity.EWorkPeriod;
import topworker.model.entity.EWorkPeriod_;

@Repository
public class WorkPeriodDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserDao userDao;

	@Transactional(value = "PlatformTransactionManager")
	public void postTime(WorkPeriod timeStamp) {
		EWorkPeriod e = getByStartDate(timeStamp.getStart());
		if (e == null) {
			e = new EWorkPeriod();
			e.setStart(timeStamp.getStart());
		}
		e.setStop(timeStamp.getStop());

		e.setUser(userDao.getUserById(1l));
		System.out.println("KURWA STOP" + e.getStop());
		entityManager.persist(e);
		// entityManager.merge(e);
	}

	@Transactional(value = "PlatformTransactionManager")
	public List<EWorkPeriod> getAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EWorkPeriod> cq = cb.createQuery(EWorkPeriod.class);
		Root<EWorkPeriod> c = cq.from(EWorkPeriod.class);
		cq.orderBy(cb.asc(c.get(EWorkPeriod_.start)));
		Query q = entityManager.createQuery(cq);
		return q.getResultList();
	}

	@Transactional(value = "PlatformTransactionManager")
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

}
