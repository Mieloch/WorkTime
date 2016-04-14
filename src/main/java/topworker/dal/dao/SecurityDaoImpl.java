package topworker.dal.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topworker.dal.SecurityDao;
import topworker.dal.entity.Security;
import topworker.dal.entity.Security_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by echomil on 22.03.16.
 */
@Repository(value = "SecurityDaoImpl")
@Transactional(value = "PlatformTransactionManager")
public class SecurityDaoImpl implements SecurityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getRecord(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Security> cq = cb.createQuery(Security.class);
        Root<Security> root = cq.from(Security.class);
        cq.where(root.get(Security_.name).in(name));
        Query q = entityManager.createQuery(cq);
        Security result;
        try {
            result = (Security) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return result.getKey();
    }
}
