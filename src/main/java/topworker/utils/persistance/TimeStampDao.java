package topworker.utils.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.entity.TimeStamp;
import topworker.model.entity.TimeStamp_;

@Repository
public class TimeStampDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(value = "PlatformTransactionManager")
	public void postTime(TimeStamp timeStamp) {

		entityManager.persist(timeStamp);
	}

	@Transactional(value = "PlatformTransactionManager")
	public List<TimeStamp> getAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeStamp> cq = cb.createQuery(TimeStamp.class);
		Root<TimeStamp> c = cq.from(TimeStamp.class);
		cq.orderBy(cb.asc(c.get(TimeStamp_.time)));
		Query q = entityManager.createQuery(cq);
		return q.getResultList();
	}
}
