package topworker.model.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Echomil on 2016-02-25.
 */
public class User {
    private String login;

    private String password;

    private String email;

    private Set<UserRole> userRoles;

    private List<WorkPeriod> workPeriods;

    private boolean active;

    public User() {
        userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);
        workPeriods = new ArrayList<>();
        active = false;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);
        workPeriods = new ArrayList<>();
        active = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<WorkPeriod> getWorkPeriods() {
        return workPeriods;
    }

    public void setWorkPeriods(List<WorkPeriod> workPeriods) {
        this.workPeriods = workPeriods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (active != user.active) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return userRoles.equals(user.userRoles);

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + userRoles.hashCode();
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
