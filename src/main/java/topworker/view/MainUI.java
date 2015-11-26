package topworker.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI
public class MainUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;

	private HorizontalLayout rootLayout;

	private Navigator navigator;
	private Panel contentPanel;

	public MainUI() {
		System.out.println("ui");
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		initPanel();
		initNavigator();
	}

	private void initPanel() {
		contentPanel = new Panel();
		rootLayout = new HorizontalLayout();
		setContent(rootLayout);
		rootLayout.setSizeFull();

		VerticalLayout menuLayout = new VerticalLayout();
		menuLayout.setSizeFull();
		Label l = new Label("root");
		l.setSizeFull();
		menuLayout.addComponent(l);

		HorizontalLayout contentLayout = new HorizontalLayout();
		contentLayout.setSizeFull();
		contentLayout.addComponent(contentPanel);

		contentPanel.setSizeFull();
		rootLayout.addComponent(menuLayout);
		rootLayout.addComponent(contentLayout);
		rootLayout.setExpandRatio(contentLayout, 5.0f);
		rootLayout.setExpandRatio(menuLayout, 1.0f);

	}

	private void initNavigator() {

		getPage().setTitle("Top Worker");
		navigator = new Navigator(this, contentPanel);
		navigator.addProvider(viewProvider);

	}

}