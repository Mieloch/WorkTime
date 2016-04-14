package topworker.view.naviagtion.calendar;

import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import topworker.model.bo.WorkPeriod;
import topworker.service.WorkPeriodService;
import topworker.utils.MessagesBundle;
import topworker.utils.TimeUtils;
import topworker.view.naviagtion.calendar.enums.CalendarRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Component
class WorkCalendarController {

    private GregorianCalendar calendar;
    private CalendarRange currentRange;
    private WorkCalendarView workCalendarView;
    private SimpleDateFormat dateFormat;

    @Autowired
    private MessagesBundle messagesBundle;

    @Autowired
    private WorkPeriodService workPeriodService;

    public WorkCalendarController() {

    }

    public void loadWorkPeriods() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<WorkPeriod> periods = workPeriodService.getAllBelongToLogedUser(auth.getName());
        for (WorkPeriod workPeriod : periods) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", messagesBundle.getLocale());
            String endTime = format.format(workPeriod.getStop());
            String startTime = format.format(workPeriod.getStart());
            CalendarEvent calEvent = new BasicEvent(startTime + "-" + endTime, TimeUtils.formatTime(workPeriod.getDuration()), workPeriod.getStart(),
                    workPeriod.getStop());

            workCalendarView.addCalendarEvent(calEvent);
        }
    }

    public void setCalendar(WorkCalendarView workCalendarView) {
        this.workCalendarView = workCalendarView;
        this.dateFormat = new SimpleDateFormat("MMMMMMM", messagesBundle.getLocale());
        calendar = new GregorianCalendar(messagesBundle.getLocale());
        setCurrentMonth();
        setMonthCaption(calendar.getTime());
    }

    private void setCurrentMonth() {

        calendar.setTime(new Date());
        setWholeMonth();
    }

    private void changeMonth(int value) {
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
        setMonthCaption(calendar.getTime());
    }

    private void changeWeek(int direction) {
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7 * direction);
        workCalendarView.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        workCalendarView.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);

    }


    public void setWeekPerspective() {
        GregorianCalendar today = new GregorianCalendar(messagesBundle.getLocale());
        today.setTime(new Date());
        if (today.get(GregorianCalendar.MONTH) == calendar.get(GregorianCalendar.MONTH)) {
            setWeek(today.get(java.util.Calendar.WEEK_OF_YEAR));
        } else {
            setWeek(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
        }
    }

    public void setMonthPerspective(String monthName) {
        setWholeMonth(monthName);
        setMonthCaption(calendar.getTime());
    }

    public void weekClicked(int week) {
        setWeek(week);
        GregorianCalendar c = new GregorianCalendar(messagesBundle.getLocale());
        c.setTime(workCalendarView.getStartDate());
        int startMonth = c.get(GregorianCalendar.MONTH);
        c.setTime(workCalendarView.getEndDate());
        int endMonth = c.get(GregorianCalendar.MONTH);
        if (startMonth == endMonth) {
            setMonthCaption(calendar.getTime());
        }
    }

    private void setWeek(int week) {

        calendar.set(GregorianCalendar.WEEK_OF_YEAR, week);
        calendar.set(GregorianCalendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        workCalendarView.setStartDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 6);
        workCalendarView.setEndDate(calendar.getTime());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -6);
        currentRange = CalendarRange.WEEK;


    }

    private void setWholeMonth(String monthName) {
        if (monthName != null) {
            try {
                Date month = dateFormat.parse(monthName);
                GregorianCalendar c = new GregorianCalendar(messagesBundle.getLocale());
                c.setTime(month);
                calendar.set(GregorianCalendar.MONTH, c.get(GregorianCalendar.MONTH));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        workCalendarView.setStartDate(calendar.getTime());
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        workCalendarView.setEndDate(calendar.getTime());
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        currentRange = CalendarRange.MONTH;
    }

    private void setWholeMonth() {

        setWholeMonth(null);
    }

    private void setMonthCaption(Date date) {
        String monthCaption = dateFormat.format(date);
        workCalendarView.setMonthLabelCaption(monthCaption);
    }
}
