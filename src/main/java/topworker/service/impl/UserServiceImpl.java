package topworker.service.impl;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import topworker.model.bo.User;
import topworker.model.bo.UserRole;
import topworker.model.dal.UserDao;
import topworker.model.dal.entity.EUser;
import topworker.model.dal.entity.EUserRoles;
import topworker.service.UserService;

import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    private ModelMapper mapper;

    public UserServiceImpl() {
        mapper = new ModelMapper();
        mapper.addConverter(getUserRoleConverter());
    }

    @Override
    public User getById(int id) {
        EUser eUser;
        try {
            eUser = userDao.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return mapToUser(eUser);
    }

    @Override
    public User getByLogin(String login) {
        EUser eUser;
        try {
            eUser = userDao.findByLogin(login);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return mapToUser(eUser);
    }

    @Override
    public void activateUser(String login) {
        EUser euser = userDao.findByLogin(login);
        euser.setActive(true);
        userDao.persist(euser);
    }

    @Override
    public void addUser(User user) {
        Set<EUserRoles> roles = userDao.getRoles(user.getUserRoles());
        EUser eUser = mapToEuser(user);
        eUser.setUserRoles(roles);
        try {
            userDao.persist(eUser);
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

    protected EUser mapToEuser(User user){
        EUser result = new EUser();
        mapper.map(user,result);
        return  result;
    }

    protected User mapToUser(EUser eUser) {
        User result = new User();
        mapper.map(eUser, result);
        return result;
    }

    private Converter<EUserRoles, UserRole> getUserRoleConverter() {
        Converter<EUserRoles, UserRole> converter = new AbstractConverter<EUserRoles, UserRole>() {
            @Override
            protected UserRole convert(EUserRoles source) {
                String type = source.getType();
                return UserRole.valueOf(type);
            }
        };
        return converter;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
