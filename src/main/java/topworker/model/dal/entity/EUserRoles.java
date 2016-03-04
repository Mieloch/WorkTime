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
}
