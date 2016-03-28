package topworker.view.naviagtion.calendar;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.*;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Locale;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@SpringView(name = WorkCalendarViewImpl.VIEW_NAME)
public class WorkCalendarViewImpl extends HorizontalLayout implements View, WorkCalendarView {
    public static final String VIEW_NAME = "calendar";

    private Calendar calendarComponent;
    private OptionGroup perspectiveOption;
    private VerticalLayout leftLayout;
    private HorizontalLayout navigationLayout;
    private VerticalLayout midLayout;
    private Label monthNameLabel;
    @Autowired
    private MessagesBundle messagesBundle;
    @Autowired
    private WorkCalendarController calendarController;
    private Locale currentLocale;

    @PostConstruct
    void init() {
        initLayout();
        createComponents();
        addComponents();
        calendarController.setCalendar(this);
        currentLocale = messagesBundle.getLocale();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (!currentLocale.equals(messagesBundle.getLocale())) {
            removeAllComponents();
            init();
        }
        calendarController.loadWorkPeriods();

    }

    private void initLayout() {
        leftLayout = new VerticalLayout();
        midLayout = new VerticalLayout();
        midLayout.setSizeFull();
        leftLayout.setMargin(true);
        addComponent(leftLayout);
        addComponent(midLayout);
        setExpandRatio(leftLayout, 1.0f);
        setExpandRatio(midLayout, 6.0f);
        navigationLayout = new HorizontalLayout();
        navigationLayout.setSizeFull();
        navigationLayout.setSpacing(true);
        leftLayout.addComponent(navigationLayout);
        setSizeFull();
    }

    private void addComponents() {
        midLayout.addComponent(monthNameLabel);
        midLayout.addComponent(calendarComponent);

        midLayout.setComponentAlignment(monthNameLabel, Alignment.MIDDLE_CENTER);
        midLayout.setExpandRatio(monthNameLabel, 1.5f);
        midLayout.setExpandRatio(calendarComponent, 9.0f);

        Button leftButton = createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(-1);
            }
        }, "left-arrow");
        leftButton.setSizeFull();
        navigationLayout.addComponent(leftButton);
        Button rightButton = createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(1);
            }
        }, "right-arrow");
        rightButton.setSizeFull();
        navigationLayout.addComponent(rightButton);
        leftLayout.addComponent(perspectiveOption);
        leftLayout.setComponentAlignment(perspectiveOption, Alignment.MIDDLE_CENTER);
    }

    private void createComponents() {
        createCalendar();
        monthNameLabel = new Label("");
        monthNameLabel.setSizeUndefined();
        monthNameLabel.addStyleName("month-label");
        perspectiveOption = createPerspectiveOption();

    }

    private void createCalendar() {

        calendarComponent = new Calendar();
        calendarComponent.setWidth(90f, Unit.PERCENTAGE);
        calendarComponent.setHeight(90f, Unit.PERCENTAGE);
        calendarComponent.setLocale(messagesBundle.getLocale());
        calendarComponent.setHandler((DateClickHandler) null);
        calendarComponent.setHandler((EventMoveHandler) null);
        calendarComponent.setHandler((EventResizeHandler) null);

        calendarComponent.setHandler(new WeekClickHandler() {

            @Override
            public void weekClick(WeekClick event) {
                perspectiveOption.select("week");
                calendarController.weekClicked(event.getWeek());

            }
        });
    }

    private OptionGroup createPerspectiveOption() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addItem("week");
        optionGroup.setItemCaption("week", messagesBundle.getMessage("calendar_week_perspective"));
        optionGroup.addItem("month");
        optionGroup.setItemCaption("month", messagesBundle.getMessage("calendar_month_perspective"));
        optionGroup.select("month");
        optionGroup.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String value = (String) event.getProperty().getValue();
                switch (value) {
                    case "week":
                        calendarController.setWeekPerspective();
                        break;
                    case "month":
                        calendarController.setMonthPerspective(monthNameLabel.getValue());
                        break;
                }
            }
        });
        return optionGroup;
    }

    private Button createButton(String caption, ClickListener listener, String style) {

        Button b = new Button(caption);
        if (style != null) {
            b.addStyleName(style);
        }
        b.addClickListener(listener);
        return b;
    }

    @Override
    public void addCalendarEvent(CalendarEvent calEvent) {
        calendarComponent.addEvent(calEvent);
    }

    @Override
    public void setMonthLabelCaption(String caption) {
        monthNameLabel.setValue(caption);
    }


    @Override
    public void setStartDate(Date date) {
        calendarComponent.setStartDate(date);
    }

    @Override
    public void setEndDate(Date date) {
        calendarComponent.setEndDate(date);
    }

    @Override
    public Date getStartDate() {
        return calendarComponent.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return calendarComponent.getEndDate();
    }


}
