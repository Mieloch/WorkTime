package topworker.view.naviagtion.signup;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

/**
 * Created by echomil on 10.03.16.
 */
@SpringView(name = SignUp.VIEW_NAME)
public class SignUp extends VerticalLayout implements View {
    public static final String VIEW_NAME = "signup";

    private FormLayout form;
    private TextField login;
    private TextField email;
    private  PasswordField password;

    public SignUp() {
        initForm();
        addComponent(form);
        setSizeFull();
        setComponentAlignment(form, Alignment.MIDDLE_CENTER);
    }

    public void initForm() {
        form = new FormLayout();
        form.setSizeUndefined();
        initFields();
        Button submit = new Button("Zarejestruj");
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login.validate();
                    password.validate();
                    email.validate();
                    //TODO tworzenie konta
                } catch (Exception e) {
                    setValidationVisible(true);
                }

            }
        });
        form.addComponent(login);
        form.addComponent(password);
        form.addComponent(email);
        form.addComponent(submit);

    }

    private void initFields() {
        login = new TextField("Login");
        login.addValidator(new EmptyFieldValidator("Pole musi byc wypelnione"));
        password = new PasswordField("Haslo");
        password.addValidator(new EmptyFieldValidator("Pole musi byc wypelnione"));
        email = new TextField("E-mail");
        email.addValidator(new EmailValidator("Niepoprawny email"));
        email.addValidator(new EmptyFieldValidator("Pole musi byc wypelnione"));
        setValidationVisible(false);
    }

    private void setValidationVisible(boolean value) {
        login.setValidationVisible(value);
        email.setValidationVisible(value);
        password.setValidationVisible(value);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

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
