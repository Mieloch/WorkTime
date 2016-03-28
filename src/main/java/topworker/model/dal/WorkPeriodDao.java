package topworker.model.dal;

import topworker.model.bo.WorkPeriod;
import topworker.model.dal.entity.EWorkPeriod;

import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodDao {
    List<EWorkPeriod> getFromDateToDate(Date startDate, Date endDate, String login);

    void postTime(WorkPeriod timeStamp);

    List<EWorkPeriod> getAll();

    EWorkPeriod getByStartDate(Date startDate);

    List<EWorkPeriod> getAllStartingIn(Date start);

    void postTimeToUser(String user, WorkPeriod period);

    List<EWorkPeriod> getAllBelongToUser(String user);
}
