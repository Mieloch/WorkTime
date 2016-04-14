package topworker.service;

import topworker.dal.entity.WorkDay;
import topworker.dal.entity.WorkPeriod;

import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodService {


    List<WorkPeriod> getAllBelongToLogedUser(String login);

    Integer getMonthlySumInMinutes(Date month, String login);

    List<WorkDay> getWorkDays(Date begin, Date end, String login);

    void postTimeToUser(String user, WorkPeriod period);

}
