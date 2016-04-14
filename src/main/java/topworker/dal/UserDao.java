package topworker.dal;

import topworker.dal.entity.User;
import topworker.dal.entity.UserRoles;
import topworker.model.bo.UserRole;

import java.util.List;
import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */
public interface UserDao {
    User findByLogin(String login);

    User findById(int id);

    void persist(User entity);

    Set<UserRoles> getRoles(Set<UserRole> roles);

    List<User> getAll();
}
