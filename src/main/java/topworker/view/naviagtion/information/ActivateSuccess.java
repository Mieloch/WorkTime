package topworker.view.naviagtion.information;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 23.03.16.
 */

@SpringView(name = ActivateSuccess.VIEW_NAME)
public class ActivateSuccess extends SimpleInformationPage {
    public static final String VIEW_NAME = "activate/success";

    @Autowired
    private MessagesBundle messagesBundle;

    @PostConstruct
    private void init() {
        setInformationMessage(messagesBundle.getMessage("activation_success_message"));
        setTitleLabel(messagesBundle.getMessage("activation_success_label"));
        currentLocale = messagesBundle.getLocale();

    }

    public ActivateSuccess() {
        super();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!currentLocale.equals(messagesBundle.getLocale())) {
            removeAllComponents();
            init();
        }
    }
}
