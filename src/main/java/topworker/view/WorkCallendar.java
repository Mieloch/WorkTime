package topworker.view;

import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = WorkCallendar.VIEW_NAME)
public class WorkCallendar extends VerticalLayout implements View {
	public static final String VIEW_NAME = "callendar";

	private Calendar calendarComponent;
	private GregorianCalendar calendar;

	@PostConstruct
	void init() {
		calendar = new GregorianCalendar();
		initLayout();
		addComponents();
	}

	private void initLayout() {

	}

	private void addComponents() {
		createComponents();
		addComponent(new Label("kalendarz"));
		addComponent(calendarComponent);
		setComponentAlignment(calendarComponent, Alignment.MIDDLE_CENTER);
	}

	private void createComponents() {
		calendarComponent = createCalendar();
	}

	private Calendar createCalendar() {
		Calendar calcComponent = new Calendar();
		calcComponent.setSizeFull();
		// Calendar
		calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		calcComponent.setStartDate(calendar.getTime());
		calendar.add(GregorianCalendar.MONTH, 1);
		calcComponent.setEndDate(calendar.getTime());
		return calcComponent;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEW 1");

	}
}
