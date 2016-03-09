package topworker.view;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import topworker.event.LoginEvent;
import topworker.service.AuthenticationService;
import topworker.utils.ViewChangeSecurityChecker;
import topworker.view.calendar.WorkCalendarView;
import topworker.view.home.Home;
import topworker.view.login.Login;
import topworker.view.summary.SummaryView;

@Theme("topworkertheme")
@SpringUI()
public class MainUI extends UI {



    @Autowired
    private SpringViewProvider viewProvider;
    private VerticalLayout rootLayout;

    private Navigator navigator;
    private Panel contentPanel;
    private HorizontalLayout contentLayout;
    private HorizontalLayout upperLayout;
    private HorizontalLayout bottomLayout;
    private Button loginButton;
    private Button logoutButton;


    public MainUI() {

    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Work Time");
        logoutButton = createLogoutButton();
        loginButton = createLoginButton();
        initRootLayout();
        initNavigator();
    }


    public void handleLogoutEvent() {
        AuthenticationService authenticationService = new AuthenticationService();
        try {
            navigator.navigateTo(Home.VIEW_NAME);
            authenticationService.handleLogout();
            removeNavigationButtons();
            addLoginButton();
        } catch (BadCredentialsException exception) {
            Notification.show("Błąd przy wylogowaniu", Notification.Type.ERROR_MESSAGE);
        }

    }
    @EventListener
    public void handleLoginEvent(LoginEvent loginEvent) {
        AuthenticationService authenticationService = new AuthenticationService();
        try {
            authenticationService.handleAuthentication(loginEvent.getLogin(), loginEvent.getPassword());
            upperLayout.removeAllComponents();
            addNavigationButtons();
            navigator.navigateTo(WorkCalendarView.VIEW_NAME);
        } catch (BadCredentialsException exception) {
            Notification.show("Błąd przy logowaniu", Notification.Type.ERROR_MESSAGE);
        }

    }

    private void initNavigator() {

        navigator = new Navigator(this, contentPanel);
        navigator.addViewChangeListener(new ViewChangeSecurityChecker());
        navigator.addProvider(viewProvider);
        navigator.addView("",Home.class);

    }

    private void addNavigationButtons() {
        Button calendarButton = createNavigationButton("", WorkCalendarView.VIEW_NAME, "calendar");
        Button listButton = createNavigationButton("", SummaryView.VIEW_NAME, "list");
        upperLayout.addComponent(calendarButton);
        upperLayout.addComponent(listButton);
        upperLayout.addComponent(logoutButton);
        upperLayout.setExpandRatio(calendarButton,1f);
        upperLayout.setExpandRatio(listButton,1f);
        upperLayout.setExpandRatio(logoutButton,8f);
        upperLayout.setComponentAlignment(listButton, Alignment.MIDDLE_CENTER);
        upperLayout.setComponentAlignment(calendarButton, Alignment.MIDDLE_CENTER);
        upperLayout.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
    }

    private void removeNavigationButtons(){
        upperLayout.removeAllComponents();
    }


    private void initRootLayout() {
        rootLayout = new VerticalLayout();
        setContent(rootLayout);
        rootLayout.setSizeFull();
        rootLayout.addStyleName("menu-panel");

        initBottomLayout();
        initContentLayout();
        initUpperLayout();

        rootLayout.addComponent(upperLayout);
        rootLayout.addComponent(contentLayout);
        rootLayout.addComponent(bottomLayout);

        rootLayout.setExpandRatio(upperLayout, 1.5f);
        rootLayout.setExpandRatio(contentLayout, 6.0f);
        rootLayout.setExpandRatio(bottomLayout, 1.0f);

    }
    private void initUpperLayout(){
        upperLayout = new HorizontalLayout();
        upperLayout.setSizeFull();
        addLoginButton();
        /*upperLayout.setHeight(100f, Unit.PERCENTAGE);
        upperLayout.setWidth(20f,Unit.PERCENTAGE);*/
    }
    private void initContentLayout() {
        contentLayout = new HorizontalLayout();
        contentLayout.addStyleName("content-panel");
        contentLayout.setSizeFull();
        contentPanel = new Panel();
        contentPanel.setSizeFull();
        contentLayout.addComponent(contentPanel);
    }

    private void initBottomLayout() {
        bottomLayout = new HorizontalLayout();
        bottomLayout.setSizeFull();
        bottomLayout.addStyleName("menu-panel");
    }


    private Button createNavigationButton(String caption, final String navigationDestination, String style) {
        Button button = new Button(caption);
        button.setWidth(80f, Unit.PERCENTAGE);
        button.setHeight(80f, Unit.PERCENTAGE);

        button.addStyleName(style);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(navigationDestination);
            }
        });
        return button;
    }

    private Button createLogoutButton(){
        Button logoutButton = new Button("");
        logoutButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                handleLogoutEvent();
            }
        });
        logoutButton.addStyleName("logout");
        logoutButton.setWidth(10f, Unit.PERCENTAGE);
        logoutButton.setHeight(80f, Unit.PERCENTAGE);
        return logoutButton;
    }

    private Button createLoginButton(){
        Button loginButton = new Button("");
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(Login.VIEW_NAME);
            }
        });
        loginButton.addStyleName("login");
        loginButton.setWidth(8f, Unit.PERCENTAGE);
        loginButton.setHeight(80f, Unit.PERCENTAGE);
        return loginButton;
    }

    private void addLoginButton(){
        upperLayout.addComponent(loginButton);
        upperLayout.setComponentAlignment(loginButton,Alignment.MIDDLE_RIGHT);
    }

}