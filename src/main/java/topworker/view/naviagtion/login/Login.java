package topworker.view.naviagtion.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import topworker.event.LoginEvent;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 06.03.16.
 */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@SpringView(name = Login.VIEW_NAME)
public class Login extends VerticalLayout implements View {

    public static final String VIEW_NAME = "login";

    private TextField loginField;

    private PasswordField passwordField;

    private VerticalLayout content;
    private Label loginLabel;

    @Autowired
    private MessagesBundle messagesBundle;

    @Autowired
    private ApplicationEventPublisher publisher;

    public Login() {
    }

    @PostConstruct
    private void init() {
        setMargin(true);
        setSizeFull();
        createComponents();
        addComponent(loginLabel);
        setComponentAlignment(loginLabel, Alignment.MIDDLE_CENTER);
        addComponent(content);
        setComponentAlignment(content, Alignment.TOP_CENTER);
        setExpandRatio(loginLabel, 1.5f);
        setExpandRatio(content, 9f);
    }

    private void createComponents() {
        content = new VerticalLayout();
        content.setSizeUndefined();
        loginLabel = new Label(messagesBundle.getMessage("log_in_label"));
        loginLabel.addStyleName("welcome-label");
        FormLayout form = new FormLayout();
        form.setSizeUndefined();
        loginField = new TextField(messagesBundle.getMessage("login_field"));
        passwordField = new PasswordField(messagesBundle.getMessage("password_field"));
        Button loginButton = createLoginButton();
        loginButton.addShortcutListener(new Button.ClickShortcut(loginButton,ShortcutAction.KeyCode.ENTER));
        form.addComponent(loginField);
        form.addComponent(passwordField);
        content.addComponent(form);
        content.addComponent(loginButton);
        content.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);
    }

    private Button createLoginButton() {
        Button button = new Button(messagesBundle.getMessage("log_in_label"));

        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                LoginEvent loginEvent = new LoginEvent(loginField.getValue(), passwordField.getValue());
                publisher.publishEvent(loginEvent);
                loginField.setValue("");
                passwordField.setValue("");
            }
        });
        return button;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event == null) {
            removeAllComponents();
            init();
        }
    }
}
