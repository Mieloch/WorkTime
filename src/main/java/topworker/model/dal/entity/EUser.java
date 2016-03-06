package topworker.model.dal.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by echomil on 03.03.16.
 */
@Entity
@Table(name = "USERS")
public class EUser {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_USER_ROLES", joinColumns = {@JoinColumn(name = "USERS_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ROLES_ID")})
    private Set<EUserRoles> userRoles;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    public Set<EUserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<EUserRoles> userRoles) {
        this.userRoles = userRoles;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Created by echomil on 04.03.16.
     */


}
