package topworker.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import topworker.model.bo.WorkPeriod;
import topworker.model.dal.WorkPeriodDao;
import topworker.model.dal.entity.EWorkPeriod;

import java.util.*;

/**
 * Created by Echomil on 2016-02-26.
 */
public class WorkPeriodServiceTest {

    private WorkPeriodDao mockedWorkPeriodDao;
    private WorkPeriodServiceImpl workPeriodService;
    private List<EWorkPeriod> eWorkPeriodList;
    private Date start;
    private Date end;

    @Before
    public void setUp() throws Exception {
        workPeriodService = new WorkPeriodServiceImpl();
        GregorianCalendar calendar = new GregorianCalendar();
        eWorkPeriodList = new ArrayList<>();
        start = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        end = calendar.getTime();
        eWorkPeriodList.add(new EWorkPeriod(start, end));

    }



    @Test
    public void mapperTest() {
        WorkPeriod workPeriod = new WorkPeriod(start, end);
        EWorkPeriod eWorkPeriod = new EWorkPeriod(start, end);
        ModelMapper mapper = new ModelMapper();
        WorkPeriod mappingResult = new WorkPeriod();
        mapper.map(eWorkPeriod, mappingResult);
        Assert.assertEquals(mappingResult, workPeriod);
    }

    @Test
    public void testGetFromDateToDate() throws Exception {
        List<WorkPeriod> workPeriodList = new ArrayList<>();
        workPeriodList.add(new WorkPeriod(start, end));
        mockedWorkPeriodDao = Mockito.mock(WorkPeriodDao.class);
        Mockito.when(mockedWorkPeriodDao.getFromDateToDate(start, end, "")).thenReturn(new ArrayList<EWorkPeriod>(eWorkPeriodList));
        workPeriodService.setWorkPeriodDao(mockedWorkPeriodDao);
        Assert.assertEquals(workPeriodList, workPeriodService.getFromDateToDate(start, end, ""));
    }

    @Test
    public void testGetAll() throws Exception {
        List<WorkPeriod> workPeriodList = new ArrayList<>();
        workPeriodList.add(new WorkPeriod(start, end));
        mockedWorkPeriodDao = Mockito.mock(WorkPeriodDao.class);
        Mockito.when(mockedWorkPeriodDao.getAll()).thenReturn(new ArrayList<EWorkPeriod>(eWorkPeriodList));
        workPeriodService.setWorkPeriodDao(mockedWorkPeriodDao);
        Assert.assertEquals(workPeriodList, workPeriodService.getAll());
    }
}