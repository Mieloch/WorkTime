package topworker.model.dal;

import topworker.model.dal.entity.EUser;

/**
 * Created by echomil on 04.03.16.
 */
public interface UserDao {
    EUser findByLogin(String login);
    EUser findById(int id);
}
