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
    private ApplicationEventPublisher publisher;

    public Login() {

        setMargin(true);
        setSizeFull();
        init();
        addComponent(loginLabel);
        setComponentAlignment(loginLabel, Alignment.MIDDLE_CENTER);
        addComponent(content);
        setComponentAlignment(content, Alignment.TOP_CENTER);
        setExpandRatio(loginLabel, 1.5f);
        setExpandRatio(content, 9f);

    }

    private void init() {
        content = new VerticalLayout();
        content.setSizeUndefined();
        loginLabel = new Label("Zalogj");
        loginLabel.addStyleName("welcome-label");
        FormLayout form = new FormLayout();
        form.setSizeUndefined();
        loginField = new TextField("Login");
        passwordField = new PasswordField("Haslo");
        Button loginButton = createLoginButton();
        loginButton.addShortcutListener(new Button.ClickShortcut(loginButton,ShortcutAction.KeyCode.ENTER));
        form.addComponent(loginField);
        form.addComponent(passwordField);
        content.addComponent(form);
        content.addComponent(loginButton);
        content.setComponentAlignment(loginButton, Alignment.MIDDLE_CENTER);

    }

    private Button createLoginButton() {
        Button button = new Button("Zaloguj");

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
    }
}
