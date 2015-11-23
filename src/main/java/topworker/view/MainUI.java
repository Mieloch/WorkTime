package topworker.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI
public class MainUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;

	private HorizontalLayout rootLayout;
	private VerticalLayout navigationLayout;
	private VerticalLayout contentLayout;

	private Navigator navigator;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		initLayout();
		initNavigationPanel();
		initContentPanel();
		// initViewPanel();
	}

	private void initLayout() {
		rootLayout = new HorizontalLayout();
		rootLayout.setSizeFull();
		rootLayout.setMargin(true);
		rootLayout.setSpacing(true);
		setContent(rootLayout);
	}

	private void initNavigationPanel() {
		Panel navigationPanel = new Panel();
		navigationPanel.setHeight(100, Unit.PERCENTAGE);
		navigationPanel.setWidth(20, Unit.PERCENTAGE);
		navigationLayout = new VerticalLayout();
		navigationLayout.setWidth(100, Unit.PERCENTAGE);
		navigationLayout.addComponent(createMoveButton(Menu.VIEW_NAME));
		navigationPanel.setContent(navigationLayout);
		rootLayout.addComponent(navigationPanel);
	}

	private void initContentPanel() {
		Panel contentPanel = new Panel();

		// contentPanel.setHeight(100, Unit.PERCENTAGE);
		// contentPanel.setWidth(100, Unit.PERCENTAGE);
		contentLayout = new VerticalLayout();
		contentLayout.setWidth(100, Unit.PERCENTAGE);
		contentPanel.setWidth(100, Unit.PERCENTAGE);
		contentPanel.setContent(contentLayout);
		rootLayout.addComponent(contentPanel);

	}

	private void initViewPanel() {
		Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		rootLayout.addComponent(viewContainer);
		rootLayout.setExpandRatio(viewContainer, 1.0f);

		navigator = new Navigator(this, viewContainer);
		navigator.addProvider(viewProvider);

	}

	private Button createMoveButton(final String destination) {
		Button btn = new Button("click");
		btn.setSizeFull();
		btn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(destination);

			}
		});
		return btn;
	}
}