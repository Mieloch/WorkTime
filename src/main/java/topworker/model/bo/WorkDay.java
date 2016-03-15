package topworker.model.bo;


import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public class WorkDay {

    private List<WorkPeriod> workPeriods;

    private int workDurationMinutes;

    private  Date date;

    public WorkDay(List<WorkPeriod> periods, Date date) {
        this.workPeriods = new ArrayList<>();
        workDurationMinutes = 0;
        this.date = date;

        for (WorkPeriod period : periods) {
            if (DateUtils.isSameDay(period.getStart(),date)) {
                workDurationMinutes += (period.getStop().getTime() - period.getStart().getTime()) / 60000;
                this.workPeriods.add(period);
            }
        }
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
