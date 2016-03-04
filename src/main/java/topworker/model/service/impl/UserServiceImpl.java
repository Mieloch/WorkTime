package topworker.model.service.impl;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.model.bo.User;
import topworker.model.bo.UserRole;
import topworker.model.dal.UserDao;
import topworker.model.dal.entity.EUser;
import topworker.model.dal.entity.EUserRoles;
import topworker.model.service.UserService;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 04.03.16.
 */
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
        EUser eUser = userDao.findById(id);
        return mapToUser(eUser);
    }

    @Override
    public User getByLogin(String login) {
        EUser eUser = userDao.findByLogin(login);
        return mapToUser(eUser);
    }

    protected User mapToUser(EUser eUser){
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
