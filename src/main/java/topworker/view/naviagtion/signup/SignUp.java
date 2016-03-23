package topworker.view.naviagtion.signup;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.model.bo.User;
import topworker.service.EmailService;
import topworker.service.UserService;
import topworker.utils.MessagesBundle;
import topworker.view.naviagtion.information.Home;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 10.03.16.
 */
@SpringView(name = SignUp.VIEW_NAME)
public class SignUp extends VerticalLayout implements View {
    public static final String VIEW_NAME = "signup";

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private MessagesBundle messagesBundle;

    private FormLayout form;
    private TextField login;
    private TextField email;
    private PasswordField password;
    private Label registerLabel;

    public SignUp() {

    }

    @PostConstruct
    private void init() {
        initForm();
        addComponent(registerLabel);
        addComponent(form);
        setSizeFull();
        setExpandRatio(registerLabel, 1.5f);
        setExpandRatio(form, 9f);
        setComponentAlignment(form, Alignment.TOP_CENTER);
        setComponentAlignment(registerLabel, Alignment.MIDDLE_CENTER);
    }

    public void initForm() {
        registerLabel = new Label(messagesBundle.getMessage("sign_up_label"));
        registerLabel.addStyleName("welcome-label");

        form = new FormLayout();
        form.setSizeUndefined();
        initFields();
        Button submit = new Button(messagesBundle.getMessage("sign_up_label"));
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login.validate();
                    password.validate();
                    email.validate();
                    User user = new User(login.getValue(), password.getValue(), email.getValue());
                    userService.addUser(user);
                    emailService.sendRegistrationMsg(user.getEmail(), user.getLogin());
                    UI.getCurrent().getNavigator().navigateTo(Home.VIEW_NAME);
                    showSuccessMsg("Aktywuj konto klikajac w link na twojej skrzynce pocztowej");
                    clearFields();

                } catch (IllegalArgumentException e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                } catch (Validator.InvalidValueException e) {
                    setValidationVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        form.addComponent(login);
        form.addComponent(password);
        form.addComponent(email);
        form.addComponent(submit);

    }

    private void showSuccessMsg(String msg) {
        Notification registerNotification = new Notification(msg);
        registerNotification.setDelayMsec(-1);
        registerNotification.show(Page.getCurrent());
    }

    private void initFields() {
        login = new TextField("Login");
        login.addValidator(new EmptyFieldValidator(messagesBundle.getMessage("empty_field_error")));
        password = new PasswordField("Haslo");
        password.addValidator(new EmptyFieldValidator(messagesBundle.getMessage("empty_field_error")));
        email = new TextField("E-mail");
        email.addValidator(new EmailValidator(messagesBundle.getMessage("incorrect_email_error")));
        email.addValidator(new EmptyFieldValidator(messagesBundle.getMessage("empty_field_error")));
        setValidationVisible(false);
    }

    private void clearFields() {
        login.clear();
        email.clear();
        password.clear();
        password.setValue("");

    }

    private void setValidationVisible(boolean value) {
        login.setValidationVisible(value);
        email.setValidationVisible(value);
        password.setValidationVisible(value);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event == null) {
            removeAllComponents();
            init();
        }

    }

    private class EmptyFieldValidator extends AbstractStringValidator {
        public EmptyFieldValidator(String errorMessage) {
            super(errorMessage);
        }

        @Override
        protected boolean isValidValue(String value) {
            if (value.equals("") || value == null) {
                return false;
            }
            return true;
        }
    }
}
