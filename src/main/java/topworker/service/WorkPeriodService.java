package topworker.service;

import topworker.model.bo.WorkDay;
import topworker.model.bo.WorkPeriod;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */
public interface WorkPeriodService {
    List<WorkPeriod> getFromDateToDate(Date startDate, Date endDate, String login);

    List<WorkPeriod> getAll();

    List<WorkPeriod> getAllBelongToLogedUser(String login);

    Integer getMonthlySumInMinutes(Date month, String login);

    List<WorkPeriod> getAllStartingIn(Date start, String login);

    List<WorkDay> getWorkDays(Date begin, Date end, String login);

    void postTimeToUser(String user, @Valid WorkPeriod period);

}
