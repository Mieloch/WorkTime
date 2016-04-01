package topworker.dal.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;


    @OneToMany(mappedBy = "user")
    private List<EWorkPeriod> workPeriods;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    public List<EWorkPeriod> getWorkPeriods() {
        return workPeriods;
    }

    public void setWorkPeriods(List<EWorkPeriod> workPeriods) {
        this.workPeriods = workPeriods;
    }

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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EUser eUser = (EUser) o;

        if (id != eUser.id) return false;
        if (active != eUser.active) return false;
        if (!login.equals(eUser.login)) return false;
        if (!password.equals(eUser.password)) return false;
        if (!userRoles.equals(eUser.userRoles)) return false;
        if (workPeriods != null ? !workPeriods.equals(eUser.workPeriods) : eUser.workPeriods != null) return false;
        return email.equals(eUser.email);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + userRoles.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (workPeriods != null ? workPeriods.hashCode() : 0);
        result = 31 * result + email.hashCode();
        return result;
    }
/**
 * Created by echomil on 04.03.16.
 */


}
