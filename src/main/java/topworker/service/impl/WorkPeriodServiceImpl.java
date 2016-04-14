package topworker.service.impl;

import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import topworker.dal.UserDao;
import topworker.dal.WorkPeriodDao;
import topworker.dal.entity.EUser;
import topworker.dal.entity.EWorkPeriod;
import topworker.model.bo.WorkDay;
import topworker.model.bo.WorkPeriod;
import topworker.service.WorkPeriodService;

import javax.persistence.NoResultException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Echomil on 2016-02-26.
 */


@Service
public class WorkPeriodServiceImpl implements WorkPeriodService {

    @Autowired
    private WorkPeriodDao workPeriodDao;

    @Autowired
    private UserDao userDao;

    private ModelMapper modelMapper;

    public WorkPeriodServiceImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public List<WorkPeriod> getFromDateToDate(Date startDate, Date endDate, String login) {
        List<EWorkPeriod> queryResult;
        try {

            queryResult = workPeriodDao.getFromDateToDate(startDate, endDate, login);
        } catch (NoResultException exception) {
            return null;
        }
        return mapToWorkPeriod(queryResult);
    }

    @Override
    public List<WorkPeriod> getAllStartingIn(Date day, String login) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(day);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);

        Calendar stopDate = Calendar.getInstance();
        stopDate.setTime(day);
        stopDate.set(Calendar.HOUR_OF_DAY, 23);
        stopDate.set(Calendar.MINUTE, 59);

        List<EWorkPeriod> queryResult;
        try {
            queryResult = workPeriodDao.getFromDateToDate(startDate.getTime(), stopDate.getTime(), login);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return mapToWorkPeriod(queryResult);
    }

    @Override
    public List<WorkDay> getWorkDays(Date begin, Date end, String login) {
        List<WorkDay> workDayList = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        beginDate.setTime(begin);
        endDate.setTime(end);
        long diff = (endDate.getTimeInMillis() - beginDate.getTimeInMillis());
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int i = 0;
        do {
            List<WorkPeriod> periodsFromDay = getAllStartingIn(beginDate.getTime(), login);
            if (!periodsFromDay.isEmpty()) {
                workDayList.add(new WorkDay(periodsFromDay, beginDate.getTime()));
            }
            beginDate.add(Calendar.DAY_OF_YEAR, 1);
            i++;
        } while (i < days);
        return workDayList;
    }

    @Override
    public void postTimeToUser(String user, WorkPeriod period) {
        Date roundedStart = DateUtils.round(period.getStart(), Calendar.SECOND);
        Date roundedStop = DateUtils.round(period.getStop(), Calendar.SECOND);
        period.setStart(roundedStart);
        period.setStop(roundedStop);
        EWorkPeriod eWorkPeriod = workPeriodDao.postTimeToUser(user, period);
        if (eWorkPeriod == null) {
            eWorkPeriod = new EWorkPeriod(period.getStart(), period.getStop());
            EUser eUser = userDao.findByLogin(user);
            eWorkPeriod.setUser(eUser);
            workPeriodDao.persist(eWorkPeriod);
        } else {
            eWorkPeriod.setStop(period.getStop());
            workPeriodDao.persist(eWorkPeriod);
        }
    }


    @Override
    public List<WorkPeriod> getAll() {
        List<EWorkPeriod> queryResult;
        try {
            queryResult = workPeriodDao.getAll();
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return mapToWorkPeriod(queryResult);
    }

    @Override
    public List<WorkPeriod> getAllBelongToLogedUser(String login) {
        return mapToWorkPeriod(workPeriodDao.getAllBelongToUser(login));


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

    private List<WorkPeriod> mapToWorkPeriod(List<EWorkPeriod> eWorkPeriods) {
        modelMapper = new ModelMapper();
        Type targetListType = new TypeToken<List<WorkPeriod>>() {
        }.getType();
        return modelMapper.map(eWorkPeriods, targetListType);
    }

    public WorkPeriodDao getWorkPeriodDao() {
        return workPeriodDao;
    }

    public void setWorkPeriodDao(WorkPeriodDao workPeriodDao) {
        this.workPeriodDao = workPeriodDao;
    }
}
