package topworker.view.naviagtion.calendar;

import com.vaadin.ui.components.calendar.event.CalendarEvent;

import java.util.Date;

/**
 * Created by echomil on 28.03.16.
 */
public interface WorkCalendarView {

    void setStartDate(Date date);

    void setEndDate(Date date);

    Date getStartDate();

    Date getEndDate();

    void setMonthLabelCaption(String caption);

    void addCalendarEvent(CalendarEvent calEvent);
}
