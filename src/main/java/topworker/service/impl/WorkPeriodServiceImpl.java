package topworker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import topworker.dal.UserDao;
import topworker.dal.WorkDayDao;
import topworker.dal.WorkPeriodDao;
import topworker.dal.entity.User;
import topworker.dal.entity.WorkDay;
import topworker.dal.entity.WorkPeriod;
import topworker.service.WorkPeriodService;
import topworker.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Echomil on 2016-02-26.
 */


@Service
public class WorkPeriodServiceImpl implements WorkPeriodService {

    @Autowired
    private WorkPeriodDao workPeriodDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WorkDayDao workDayDao;


    public WorkPeriodServiceImpl() {

    }

    @Override
    public List<WorkDay> getWorkDays(Date begin, Date end, String login) {
        List<WorkDay> workDays = workDayDao.getBetweenDates(begin, end, login);
        return workDays;
    }

    @Override
    public void postTimeToUser(String login, WorkPeriod newPeriod) {
        //TODO split when period lasts 2 days
        newPeriod = TimeUtils.roundTimeToField(newPeriod, Calendar.SECOND);
        WorkDay workDay = workDayDao.getByDateAndUser(login, newPeriod.getStart());
        if (workDay == null) {
            workDay = new WorkDay();
            workDay.setDate(newPeriod.getStart());
            User user = userDao.findByLogin(login);
            workDay.setUser(user);
            workDayDao.create(workDay);
            WorkPeriod lastPeriod = new WorkPeriod(newPeriod.getStart(), newPeriod.getStop());
            lastPeriod.setWorkDay(workDay);
            workPeriodDao.persist(lastPeriod);
        } else {
            WorkPeriod lastPeriod = workPeriodDao.findLastPeriodInStreak(workDay, newPeriod);
            lastPeriod.setStop(newPeriod.getStop());
            workPeriodDao.persist(lastPeriod);
        }

    }


    @Override
    public List<WorkPeriod> getAllBelongToLogedUser(String login) {
        return workPeriodDao.getAllBelongToUser(login);
    }

    @Override
    public Integer getMonthlySumInMinutes(Date month, String login) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(month);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, gregorianCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date start = gregorianCalendar.getTime();
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = gregorianCalendar.getTime();
        List<WorkDay> workDays = getWorkDays(start, end, login);
        int sum = 0;
        for (WorkDay workDay : workDays) {
            sum += workDay.getWorkDurationMinutes();
        }
        return sum;
    }

    public WorkPeriodDao getWorkPeriodDao() {
        return workPeriodDao;
    }

    public void setWorkPeriodDao(WorkPeriodDao workPeriodDao) {
        this.workPeriodDao = workPeriodDao;
    }
}
