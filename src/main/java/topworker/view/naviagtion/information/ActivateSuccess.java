package topworker.view.naviagtion.information;

import com.vaadin.spring.annotation.SpringView;
import topworker.utils.MessagesBundle;

/**
 * Created by echomil on 23.03.16.
 */

@SpringView(name = ActivateSuccess.VIEW_NAME)
public class ActivateSuccess extends SimpleInformationPage {
    public static final String VIEW_NAME = "activate/success";

    public ActivateSuccess() {
        super();
        setInformationMessage(MessagesBundle.getMessage("activation_success_message"));
        setTitleLabel(MessagesBundle.getMessage("activation_success_label"));
    }
}
