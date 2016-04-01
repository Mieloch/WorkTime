package topworker.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import topworker.dal.UserDao;
import topworker.dal.entity.EUser;
import topworker.dal.entity.EUserRoles;
import topworker.dal.entity.EWorkPeriod;
import topworker.model.bo.User;
import topworker.model.bo.UserRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by echomil on 04.03.16.
 */
public class UserServiceImplTest {

    private UserDao mockedUserDao;

    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        mockedUserDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImpl();
        userService.setUserDao(mockedUserDao);
    }

    @Test
    public void testEUserMappedToUser() {
        User user = prepareTestUser();
        EUser eUser = prepareTestEUser();
        User result = userService.mapToUser(eUser);
        Assert.assertEquals(result, user);
    }

    @Test
    public void isUserMappedToEntityProperly(){
        EUser eUser = prepareTestEUser();
        User user = prepareTestUser();
        EUser result = userService.mapToEuser(user);
        Assert.assertEquals(result, eUser);


    }
    private User prepareTestUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        user.setActive(true);
        user.setEmail("mail@mail.com");
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(UserRole.ADMIN);
        roleSet.add(UserRole.USER);
        user.setUserRoles(roleSet);
        return user;
    }

    private EUser prepareTestEUser() {
        EUser eUser = new EUser();
        eUser.setWorkPeriods(new ArrayList<EWorkPeriod>());
        eUser.setId(0);
        eUser.setActive(true);
        eUser.setLogin("login");
        eUser.setPassword("pass");
        eUser.setEmail("mail@mail.com");
        EUserRoles roleOne = new EUserRoles();
        roleOne.setType(UserRole.ADMIN.getType());
        EUserRoles roleTwo = new EUserRoles();
        roleTwo.setType(UserRole.USER.getType());
        Set<EUserRoles> set = new HashSet<>();
        set.add(roleOne);
        set.add(roleTwo);
        eUser.setUserRoles(set);
        return eUser;
    }
}