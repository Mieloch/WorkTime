package topworker.view.calendar;

import java.util.Locale;

import javax.annotation.PostConstruct;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@SpringView(name = WorkCalendarView.VIEW_NAME)
public class WorkCalendarView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "calendar";

    private Calendar calendarComponent;
    private OptionGroup perpectiveOption;
    private VerticalLayout leftLayout;
    private HorizontalLayout navigationLayout;
    private VerticalLayout midLayout;
    private Button showMonthButton;
    private Button showWeekButton;

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
        leftLayout.addComponent(navigationLayout);
        setSizeFull();
    }

    private void addComponents() {
        createComponents();
        midLayout.addComponent(calendarComponent);

        navigationLayout.addComponent(createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(-1);
            }
        },"left-arrow"));
        navigationLayout.addComponent(createButton("", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.navigate(1);
            }
        },"right-arrow"));
        leftLayout.addComponent(perpectiveOption);
    }

    private void createComponents() {
        calendarComponent = createCalendar();
        showMonthButton = createButton("Miesiąc", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.setMonthPerspective();
            }
        },null);

        showWeekButton = createButton("Tydzień", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                calendarController.setWeekPerpective();
            }
        },null);
        perpectiveOption = createPerspectiveOption();

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
                        calendarController.setWeekPerpective();
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


}
