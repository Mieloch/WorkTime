package topworker.view.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
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
	private ComboBox monthComboBox;

	private VerticalLayout leftLayout;
	private HorizontalLayout navigationLayout;
	private VerticalLayout midLayout;
	private Button showMonthButton;

	@Autowired
	private WorkCalendarController calendarController;

	@PostConstruct
	void init() {
		initLayout();
		addComponents();
		System.out.println("contrtuct");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEW 1");
		calendarController.loadWorkPeriods();
	}

	private void initLayout() {
		leftLayout = new VerticalLayout();
		midLayout = new VerticalLayout();

		// leftLayout.setSizeFull();
		midLayout.setSizeFull();

		midLayout.setMargin(true);

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

		navigationLayout.addComponent(createButton("ml", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				calendarController.navigate(-1);
			}
		}));
		navigationLayout.addComponent(createButton("mr", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				calendarController.navigate(1);
			}
		}));
	}

	private void createComponents() {
		calendarComponent = createCalendar();
		monthComboBox = createMonthComboBox();
		showMonthButton = createButton("Pokaż miesiąc", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				calendarController.changePerspective();
				leftLayout.removeComponent(showMonthButton);
			}
		});

	}

	private Calendar createCalendar() {
		Calendar calcComponent = new Calendar();
		calcComponent.setSizeFull();
		calcComponent.setHandler((DateClickHandler) null);
		calcComponent.setHandler((EventMoveHandler) null);
		calcComponent.setHandler((EventResizeHandler) null);

		calcComponent.setHandler(new WeekClickHandler() {

			@Override
			public void weekClick(WeekClick event) {
				calendarController.setWeek(event.getWeek());
				leftLayout.addComponent(showMonthButton);
			}
		});
		calendarController.setCalendar(calcComponent);

		return calcComponent;
	}

	private Button createButton(String caption, ClickListener listener) {
		Button b = new Button(caption);
		b.addClickListener(listener);
		return b;
	}

	private ComboBox createMonthComboBox() {
		ComboBox combo = new ComboBox("Miesiąc:");
		combo.setNullSelectionAllowed(false);
		GregorianCalendar cal = new GregorianCalendar();
		combo.setHeight(50f, Unit.PERCENTAGE);
		cal.set(GregorianCalendar.MONTH, 0);
		SimpleDateFormat format = new SimpleDateFormat("MMMMMMMMMMMMMM");
		for (int i = 0; i < 12; i++) {
			combo.addItem(format.format(cal.getTime()));
			cal.add(GregorianCalendar.MONTH, 1);
		}
		combo.setValue(format.format(new Date()));
		return combo;
	}

}
