package topworker.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.model.bo.WorkDay;
import topworker.model.bo.WorkPeriod;
import topworker.model.dal.WorkPeriodDao;
import topworker.model.dal.entity.EWorkPeriod;
import topworker.service.WorkPeriodService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Echomil on 2016-02-26.
 */


public class WorkPeriodServiceImpl implements WorkPeriodService {

    @Autowired
    private WorkPeriodDao workPeriodDao;

    @Override
    public List<WorkPeriod> getFromDateToDate(Date startDate, Date endDate) {
        List<EWorkPeriod> queryResult = workPeriodDao.getFromDateToDate(startDate, endDate);
        List<WorkPeriod> result = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        Type targetListType = new TypeToken<List<WorkPeriod>>() {
        }.getType();
        result = modelMapper.map(queryResult, targetListType);
        return result;
    }

    @Override
    public void postTime(WorkPeriod workPeriod) {
        workPeriodDao.postTime(workPeriod);
    }

    @Override
    public List<WorkPeriod> getAllStartingIn(Date start) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);
        startDate.set(Calendar.HOUR_OF_DAY,0);
        startDate.set(Calendar.MINUTE,0);

        Calendar stopDate = Calendar.getInstance();
        stopDate.setTime(start);
        stopDate.set(Calendar.HOUR_OF_DAY,23);
        stopDate.set(Calendar.MINUTE,59);

        List<EWorkPeriod> queryResult = workPeriodDao.getFromDateToDate(startDate.getTime(),stopDate.getTime());
        ModelMapper modelMapper = new ModelMapper();
        List<WorkPeriod> result = new ArrayList<>();
        Type targetListType = new TypeToken<List<WorkPeriod>>() {
        }.getType();
        result = modelMapper.map(queryResult, targetListType);
        return result;
    }

    @Override
    public List<WorkDay> getWorkDays(Date begin, Date end) {
        List<WorkDay> workDayList = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        beginDate.setTime(begin);
        endDate.setTime(end);
        long diff =  (endDate.getTimeInMillis() - beginDate.getTimeInMillis());
        long days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        int i = 0;
        do {
            List<WorkPeriod> periodsFromDay = getAllStartingIn(beginDate.getTime());
            if (!periodsFromDay.isEmpty()) {
                workDayList.add(new WorkDay(periodsFromDay,beginDate.getTime()));
            }
            beginDate.add(Calendar.DAY_OF_YEAR, 1);
            i++;
        } while (i < days);
        return workDayList;
    }

    @Override
    public List<WorkPeriod> getAll() {
        List<EWorkPeriod> queryResult = workPeriodDao.getAll();
        List<WorkPeriod> result = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        Type targetListType = new TypeToken<List<WorkPeriod>>() {
        }.getType();
        result = modelMapper.map(queryResult, targetListType);
        return result;
    }

    public WorkPeriodDao getWorkPeriodDao() {
        return workPeriodDao;
    }

    public void setWorkPeriodDao(WorkPeriodDao workPeriodDao) {
        this.workPeriodDao = workPeriodDao;
    }
}
