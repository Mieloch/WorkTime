package topworker.view.naviagtion.calendar;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@SpringView(name = WorkCalendarView.VIEW_NAME)
public class WorkCalendarView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "calendar";

    private Calendar calendarComponent;
    private OptionGroup perspectiveOption;
    private VerticalLayout leftLayout;
    private HorizontalLayout navigationLayout;
    private VerticalLayout midLayout;
    private Label monthNameLabel;

    @Autowired
    private WorkCalendarController calendarController;

    @PostConstruct
    void init() {
        initLayout();
        addComponents();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        calendarController.loadWorkPeriods();
        setMonthLabelCaption(calendarComponent.getStartDate(),calendarComponent.getEndDate());

    }

    private void initLayout() {
        leftLayout = new VerticalLayout();
        midLayout = new VerticalLayout();
        midLayout.setSizeFull();
        midLayout.setMargin(true);
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
        createComponents();
        midLayout.addComponent(monthNameLabel);
        midLayout.addComponent(calendarComponent);

        midLayout.setComponentAlignment(monthNameLabel,Alignment.MIDDLE_CENTER);
        midLayout.setExpandRatio(monthNameLabel,1.5f);
        midLayout.setExpandRatio(calendarComponent,9.0f);

        Button leftButton = createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(-1);
                setMonthLabelCaption(calendarComponent.getStartDate(),calendarComponent.getEndDate());
            }
        },"left-arrow");
        leftButton.setSizeFull();
        navigationLayout.addComponent(leftButton);
        Button rightButton = createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(1);
                setMonthLabelCaption(calendarComponent.getStartDate(),calendarComponent.getEndDate());
            }
        },"right-arrow");
        rightButton.setSizeFull();
        navigationLayout.addComponent(rightButton);
        leftLayout.addComponent(perspectiveOption);
        leftLayout.setComponentAlignment(perspectiveOption, Alignment.MIDDLE_CENTER);
    }

    private void createComponents() {
        calendarComponent = createCalendar();
        monthNameLabel = new Label("");
        monthNameLabel.setSizeUndefined();
        monthNameLabel.addStyleName("month-label");
        perspectiveOption = createPerspectiveOption();

    }

    private Calendar createCalendar() {
        Calendar calcComponent = new Calendar();
        calcComponent.setSizeFull();
        calcComponent.setLocale(new Locale("pl", "PL"));
        calcComponent.setHandler((DateClickHandler) null);
        calcComponent.setHandler((EventMoveHandler) null);
        calcComponent.setHandler((EventResizeHandler) null);

        calcComponent.setHandler(new WeekClickHandler() {

            @Override
            public void weekClick(WeekClick event) {
                calendarController.setWeek(event.getWeek());
                perspectiveOption.select("week");
            }
        });
        calendarController.setCalendar(calcComponent);
        return calcComponent;
    }

    private OptionGroup createPerspectiveOption() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addItem("week");
        optionGroup.setItemCaption("week", "Tydzień");
        optionGroup.addItem("month");
        optionGroup.setItemCaption("month", "Miesiąc");
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
                        calendarController.setMonthPerspective();
                        break;
                }
            }
        });
        return optionGroup;
    }

    private Button createButton(String caption, ClickListener listener, String style) {

        Button b = new Button(caption);
        if(style != null){
            b.addStyleName(style);
        }
        b.addClickListener(listener);
        return b;
    }

    private void setMonthLabelCaption(Date begin, Date end){
        long diff =  (begin.getTime() - end.getTime());
        int days = (int)TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        DateUtils.addDays(begin,days);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMMMM",new Locale("pl","PL"));
        monthNameLabel.setValue(dateFormat.format(begin));
    }


}
