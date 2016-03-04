package topworker.model.dal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.dal.entity.metadata.EUserDetails_;
import topworker.model.dal.entity.EUserDetails;

@Repository
public class UserDetailsDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(value = "PlatformTransactionManager")
	public EUserDetails getUserById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EUserDetails> cq = cb.createQuery(EUserDetails.class);
		Root<EUserDetails> c = cq.from(EUserDetails.class);
		cq.where(c.get(EUserDetails_.id).in(id));
		Query q = entityManager.createQuery(cq);
		return (EUserDetails) q.getSingleResult();

	}
}
