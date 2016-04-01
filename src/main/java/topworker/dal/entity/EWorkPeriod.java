package topworker.dal.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WORK_PERIOD")
public class EWorkPeriod {

    @GeneratedValue
    @Id
    private long id;

    private Date start;
    private Date stop;

    @ManyToOne
    private EUser user;

    public EWorkPeriod() {
    }


    public EWorkPeriod(Date start, Date stop) {
        this.start = start;
        this.stop = stop;
    }

    public long getId() {
        return id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public EUser getUser() {
        return user;
    }

    public void setUser(EUser user) {
        this.user = user;
    }
}
