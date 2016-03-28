package topworker.service;

import topworker.model.bo.User;

import java.util.List;

/**
 * Created by echomil on 04.03.16.
 */
public interface UserService {

    User getById(int id);
    User getByLogin(String login);

    void activateUser(String login);
    void addUser(User user);

    List<User> getAll();

}
