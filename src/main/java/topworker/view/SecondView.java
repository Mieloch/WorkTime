package topworker.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = SecondView.VIEW_NAME)
public class SecondView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "SECOND_VIEW";

	@PostConstruct
	void init() {

		this.addComponent(createFirstBtn());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	private Button createFirstBtn() {
		Button button = new Button();
		button.setSizeFull();
		return button;
	}

}
