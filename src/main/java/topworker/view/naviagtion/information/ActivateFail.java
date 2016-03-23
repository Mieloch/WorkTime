package topworker.view.naviagtion.information;

import com.vaadin.spring.annotation.SpringView;
import topworker.utils.MessagesBundle;

/**
 * Created by echomil on 23.03.16.
 */

@SpringView(name = ActivateFail.VIEW_NAME)
public class ActivateFail extends SimpleInformationPage {
    public static final String VIEW_NAME = "activate/fail";

    public ActivateFail() {
        super();
        setInformationMessage(MessagesBundle.getMessage("activation_fail_message"));
        setTitleLabel(MessagesBundle.getMessage("activation_fail_label"));
    }
}