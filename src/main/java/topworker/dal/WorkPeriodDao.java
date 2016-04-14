package topworker.dal;

import topworker.dal.entity.EWorkPeriod;
import topworker.model.bo.WorkPeriod;

import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodDao {
    List<EWorkPeriod> getFromDateToDate(Date startDate, Date endDate, String login);

    void persist(EWorkPeriod timeStamp);

    List<EWorkPeriod> getAll();

    EWorkPeriod getByStartDate(Date startDate);

    List<EWorkPeriod> getAllStartingIn(Date start);

    EWorkPeriod postTimeToUser(String user, WorkPeriod period);

    List<EWorkPeriod> getAllBelongToUser(String user);
}
