package topworker.view.naviagtion.information;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 23.03.16.
 */

@SpringView(name = ActivateFail.VIEW_NAME)
public class ActivateFail extends SimpleInformationPage {
    public static final String VIEW_NAME = "activate/fail";
    @Autowired
    private MessagesBundle messagesBundle;
    public ActivateFail() {
        super();

    }

    @PostConstruct
    private void init() {
        setInformationMessage(messagesBundle.getMessage("activation_fail_message"));
        setTitleLabel(messagesBundle.getMessage("activation_fail_label"));
        currentLocale = messagesBundle.getLocale();

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!currentLocale.equals(messagesBundle.getLocale())) {
            removeAllComponents();
            init();
        }
    }
}