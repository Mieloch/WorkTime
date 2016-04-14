package topworker.dal;


import topworker.dal.entity.WorkDay;

import java.util.Date;
import java.util.List;

/**
 * Created by echomil on 14.04.16.
 */


public interface WorkDayDao {

    WorkDay getByDateAndUser(String userName, Date date);

    void create(WorkDay workDay);

    List<WorkDay> getBetweenDates(Date start, Date end, String username);


}
