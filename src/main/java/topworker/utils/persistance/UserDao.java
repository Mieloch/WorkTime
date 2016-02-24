package topworker.utils.persistance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.entity.EUser;
import topworker.model.entity.EUser_;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(value = "PlatformTransactionManager")
	public EUser getUserById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EUser> cq = cb.createQuery(EUser.class);
		Root<EUser> c = cq.from(EUser.class);
		cq.where(c.get(EUser_.id).in(id));
		Query q = entityManager.createQuery(cq);
		return (EUser) q.getSingleResult();

	}
}
