package topworker.view;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import topworker.view.calendar.WorkCalendarView;
import topworker.view.summary.SummaryView;

@Theme("topworkertheme")
@SpringUI
public class MainUI extends UI {

    @Autowired
    private SpringViewProvider viewProvider;
    private VerticalLayout rootLayout;

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
        rootLayout = new VerticalLayout();
        setContent(rootLayout);
        rootLayout.setSizeFull();

        HorizontalLayout upperLayout = new HorizontalLayout();
        upperLayout.setHeight(100f, Unit.PERCENTAGE);
        upperLayout.setMargin(true);
        upperLayout.addComponent(createNavigationButton("Kalendarz", WorkCalendarView.VIEW_NAME, "calendar"));
        upperLayout.addComponent(createNavigationButton("Statystyki", SummaryView.VIEW_NAME, "list"));

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSizeFull();
        contentLayout.addComponent(contentPanel);

        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.setSizeFull();
        Label l2 = new Label("root");
        l2.setSizeFull();
        bottomLayout.addComponent(l2);

        contentPanel.setSizeFull();
        rootLayout.addComponent(upperLayout);
        rootLayout.addComponent(contentLayout);
        rootLayout.addComponent(bottomLayout);

        rootLayout.setExpandRatio(upperLayout, 1.5f);
        rootLayout.setExpandRatio(contentLayout, 6.0f);
        rootLayout.setExpandRatio(bottomLayout, 1.0f);

    }

    private void initNavigator() {

        getPage().setTitle("Top Worker");
        navigator = new Navigator(this, contentPanel);
        navigator.addProvider(viewProvider);

    }

    private Button createNavigationButton(String caption, final String navigationDestination, String style) {
        Button button = new Button(caption);
        button.setHeight(100f, Unit.PERCENTAGE);
        button.addStyleName(style);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(navigationDestination);
            }
        });
        return button;
    }

}