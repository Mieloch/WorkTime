package topworker.dal;

import topworker.dal.entity.WorkDay;
import topworker.dal.entity.WorkPeriod;

import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodDao {

    void persist(WorkPeriod timeStamp);

    List<WorkPeriod> getAll();

    WorkPeriod getByStartDate(Date startDate);

    List<WorkPeriod> getAllStartingIn(Date start);

    WorkPeriod findLastPeriodInStreak(WorkDay workDay, WorkPeriod period);

    List<WorkPeriod> getAllBelongToUser(String user);
}
