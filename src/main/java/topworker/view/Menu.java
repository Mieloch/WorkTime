package topworker.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = Menu.VIEW_NAME)
public class Menu extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";

	@PostConstruct
	void init() {
		initLayout();
		addComponents();
	}

	private void initLayout() {

	}

	private void addComponents() {
		this.addComponent(createFirstBtn());
		this.addComponent(createFirstBtn());

	}

	private Button createFirstBtn() {
		Button button = new Button();
		button.setSizeFull();
		return button;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEW 1");

	}
}
