package topworker.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import topworker.dal.UserDao;
import topworker.dal.entity.User;
import topworker.dal.entity.UserRoles;
import topworker.service.EncryptionService;
import topworker.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EncryptionService encryptionService;


    public UserServiceImpl() {
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDao.getAll();
        return users;
    }

    @Override
    public User getById(int id) {
        User user;
        try {
            user = userDao.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return user;
    }

    @Override
    public User getByLogin(String login) {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return (user);
    }

    @Override
    public void activateUser(String login) {
        User euser = userDao.findByLogin(login);
        euser.setActive(true);
        userDao.persist(euser);
    }

    @Override
    public void addUser(User user) {

        Set<UserRoles> roles = userDao.getRoles(user.getUserRolesEnums());
        String digestPassword = encryptionService.digest(user.getPassword());
        user.setPassword(digestPassword);
        user.setRegistrationDate(new Date());
        user.setUserRoles(roles);
        try {
            userDao.persist(user);
        }  catch (DataIntegrityViolationException e ) {
            String details = e.getCause().getCause().toString();
            if(details.indexOf("email") != -1){
                throw new IllegalArgumentException("Podany E-mail jest juz zarejestrowany");
            }
            else if(details.indexOf("login") != -1){
                throw new IllegalArgumentException("Podany login jest juz zarejestrowany");
            }
            else{
                e.printStackTrace();
                throw new IllegalArgumentException("Błąd podczas zapisu uzytkownika");
            }
        }
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
