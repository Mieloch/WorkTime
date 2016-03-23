package topworker.view.naviagtion.information;

import com.vaadin.spring.annotation.SpringView;

/**
 * Created by echomil on 07.03.16.
 */
@SpringView(name = Home.VIEW_NAME)
public class Home extends SimpleInformationPage {

    public final static String VIEW_NAME = "";

    public Home() {
        super();
        setTitleLabel(resourceBundle.getString("home_label"));
        setInformationMessage(resourceBundle.getString("home_information"));
    }
}
