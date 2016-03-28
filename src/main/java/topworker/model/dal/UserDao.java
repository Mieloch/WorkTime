package topworker.model.dal;

import topworker.model.bo.UserRole;
import topworker.model.dal.entity.EUser;
import topworker.model.dal.entity.EUserRoles;

import java.util.List;
import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */
public interface UserDao {
    EUser findByLogin(String login);
    EUser findById(int id);
    void persist(EUser entity);
    Set<EUserRoles> getRoles(Set<UserRole> roles);

    List<EUser> getAll();
}
