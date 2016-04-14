package topworker.dal.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */

@Entity(name = "WORK_DAY")
public class WorkDay {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    private List<WorkPeriod> workPeriods;

    @ManyToOne
    private User user;

    @Transient
    private int workDurationMinutes;

    @Type(type = "date")
    @Column(name = "DATE", nullable = false)
    private Date date;

    public WorkDay() {
    }


    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWorkDurationMinutes() {
        return workDurationMinutes;
    }

    public void setWorkDurationMinutes(int workDurationMinutes) {
        this.workDurationMinutes = workDurationMinutes;
    }

    public List<WorkPeriod> getWorkPeriods() {
        return workPeriods;
    }

    public void setWorkPeriods(List<WorkPeriod> workPeriods) {
        this.workPeriods = workPeriods;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
