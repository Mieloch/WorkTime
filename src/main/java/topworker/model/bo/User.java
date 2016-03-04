package topworker.model.bo;

import java.util.Set;

/**
 * Created by Echomil on 2016-02-25.
 */
public class User {
    private String login;

    private String password;

    private Set<UserRole> userRoles;

    private boolean active;

    public User() {
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
