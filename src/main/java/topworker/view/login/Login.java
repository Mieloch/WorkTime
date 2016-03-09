package topworker.view.login;

import com.google.gwt.event.shared.EventBus;
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

    private Button loginButton;
    private FormLayout form;


    @Autowired
    private ApplicationEventPublisher publisher;

    public Login() {

        setMargin(true);
        setSizeFull();
        init();
        addComponent(form);
        setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        addComponent(loginButton);
        setComponentAlignment(loginButton, Alignment.TOP_CENTER);
       // setExpandRatio(form,3f);
       // setExpandRatio(loginButton,1f);
    }

    private void init() {
        form = new FormLayout();
        form.setSizeUndefined();
        loginField = new TextField("Login");
        passwordField = new PasswordField("Haslo");
        loginButton = createLoginButton();
        form.addComponent(loginField);
        form.addComponent(passwordField);

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
