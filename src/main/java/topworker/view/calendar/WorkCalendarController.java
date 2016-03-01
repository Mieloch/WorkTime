package topworker.view.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import topworker.model.WorkPeriodService;
import topworker.model.bo.WorkPeriod;
import topworker.model.dal.entity.EWorkPeriod;
import topworker.view.calendar.enums.CalendarRange;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Component
class WorkCalendarController {

    private GregorianCalendar calendar;
    private Calendar calendarComponent;
    private CalendarRange currentRange;

    @Autowired
    private WorkPeriodService workPeriodService;

    public WorkCalendarController() {
        calendar = new GregorianCalendar();
    }

    public void loadWorkPeriods() {
        List<WorkPeriod> periods = workPeriodService.getAll();
        for (WorkPeriod eWorkPeriod : periods) {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            String endDate = format.format(eWorkPeriod.getStop());
            CalendarEvent calEvent = new BasicEvent(endDate, "TODO USER", eWorkPeriod.getStart(),
                    eWorkPeriod.getStop());
            calendarComponent.addEvent(calEvent);
        }
    }

    public void setCalendar(Calendar calendarComponent) {
        this.calendarComponent = calendarComponent;
        setCurrentMonth();
    }

    public void setCurrentMonth() {

        calendar.setTime(new Date());
        setWholeMonth();
    }

    public void changeMonth(int value) {
        calendar.add(GregorianCalendar.MONTH, value);
        setWholeMonth();
    }

    public void navigate(int direction) {
        switch (currentRange) {
            case MONTH:
                changeMonth(direction);
                break;
            case WEEK:
                changeWeek(direction);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void changeWeek(int direction) {
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7 * direction);
        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);

    }

    public void changePerspective() {
        switch (currentRange) {
            case MONTH:
                setWeek(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
                break;
            case WEEK:
                setWholeMonth();
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public void setWeekPerpective(){
        setWeek(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
    }

    public void setMonthPerspective(){
        setWholeMonth();
    }

    public void setWeek(int week) {

        calendar.set(GregorianCalendar.WEEK_OF_YEAR, week);
        calendar.set(GregorianCalendar.DAY_OF_WEEK, 2);

        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);
        currentRange = CalendarRange.WEEK;

    }

    private void setWholeMonth() {
        calendar.add(java.util.Calendar.DAY_OF_MONTH,7);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        calendarComponent.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.MONTH, 1);
        calendarComponent.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.MONTH, -1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        currentRange = CalendarRange.MONTH;
    }
}
