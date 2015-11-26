package topworker.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = SecondView.VIEW_NAME)
public class SecondView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "second";

	@PostConstruct
	private void init() {
		setSizeFull();
		addComponent(new Label("Second"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("VIEW 2s");
	}

}
