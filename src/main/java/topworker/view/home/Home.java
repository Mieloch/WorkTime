package topworker.view.home;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import org.springframework.boot.test.SpringApplicationConfiguration;
import topworker.view.login.Login;

/**
 * Created by echomil on 07.03.16.
 */
@SpringView(name = Home.VIEW_NAME)
public class Home extends FormLayout implements View {

    public final static String VIEW_NAME = "";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label("HOME"));
    }
}
