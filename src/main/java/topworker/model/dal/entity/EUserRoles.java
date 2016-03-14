package topworker.model.dal.entity;

import javax.persistence.*;

/**
 * Created by echomil on 03.03.16.
 */
@Entity
@Table(name = "USER_ROLES")
public class EUserRoles {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "TYPE", unique = true, nullable = false)
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EUserRoles that = (EUserRoles) o;

        if (id != that.id) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
