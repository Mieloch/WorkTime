package topworker.service;

import topworker.model.bo.WorkDay;
import topworker.model.bo.WorkPeriod;

import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodService {
    List<WorkPeriod> getFromDateToDate(Date startDate, Date endDate);

    List<WorkPeriod> getAll();

    List<WorkPeriod> getAllBelongToUser();

    void postTime(WorkPeriod workPeriod);

    List<WorkPeriod> getAllStartingIn(Date start);

    List<WorkDay> getWorkDays(Date begin, Date end);

    void postTimeToUser(String user, WorkPeriod period);

}
