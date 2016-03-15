package topworker.model.bo;

import java.util.Date;

/**
 * Created by Echomil on 2016-02-25.
 */
public class WorkPeriod {


    private Date start;
    private Date stop;

    public WorkPeriod(Date start, Date stop) {
        this.start = start;
        this.stop = stop;
    }


    public WorkPeriod() {

    }

    public int getDuration() {
        return (int) ((stop.getTime() - start.getTime()) / 60000);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkPeriod that = (WorkPeriod) o;

        if (!start.equals(that.start)) return false;
        return stop.equals(that.stop);

    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + stop.hashCode();
        return result;
    }
}
