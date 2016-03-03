package topworker.model.dal.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WORK_PERIOD")
public class EWorkPeriod {

    @GeneratedValue
    @Id
    private long id;

    private Date start;
    private Date stop;

    @ManyToOne
    private EUserDetails userDetails;

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

    public EUserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(EUserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
