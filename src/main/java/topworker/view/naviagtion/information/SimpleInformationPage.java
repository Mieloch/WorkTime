package topworker.view.naviagtion.information;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

/**
 * Created by echomil on 23.03.16.
 */
public class SimpleInformationPage extends VerticalLayout implements View {

    private VerticalLayout content;
    private Label titleLable;
    private TextArea information;
    protected ResourceBundle resourceBundle;

    public SimpleInformationPage() {
        resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        init();
        setSizeFull();
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    public void init() {
        content = new VerticalLayout();
        content.setWidth(50f, Sizeable.Unit.PERCENTAGE);
        content.setHeight(100f, Sizeable.Unit.PERCENTAGE);
        titleLable = new Label();
        titleLable.addStyleName("welcome-label");
        information = new TextArea();
        information.setEnabled(false);
        information.setSizeFull();
        information.addStyleName("information");
        content.addComponent(titleLable);
        content.addComponent(information);
        content.setExpandRatio(titleLable, 1.5f);
        content.setExpandRatio(information, 9f);
    }


    public void setTitleLabel(String title) {
        titleLable.setValue(title);
    }


    public void setInformationMessage(String informationMessage) {
        information.setValue(informationMessage);
    }
}

