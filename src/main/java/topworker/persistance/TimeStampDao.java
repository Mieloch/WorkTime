package topworker.persistance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.entity.TimeStamp;

@Repository
public class TimeStampDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(value = "PlatformTransactionManager")
	public void postTime(TimeStamp timeStamp) {

		entityManager.persist(timeStamp);
	}
}
