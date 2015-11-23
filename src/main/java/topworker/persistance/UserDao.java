package topworker.persistance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import topworker.model.entity.User;
import topworker.model.entity.User_;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(value = "PlatformTransactionManager")
	public User getUserById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> c = cq.from(User.class);
		cq.where(c.get(User_.id).in(id));
		Query q = entityManager.createQuery(cq);
		return (User) q.getSingleResult();

	}
}
