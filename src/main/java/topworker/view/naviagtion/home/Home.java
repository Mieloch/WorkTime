package topworker.view.naviagtion.home;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by echomil on 07.03.16.
 */
@SpringView(name = Home.VIEW_NAME)
public class Home extends VerticalLayout implements View {

    public final static String VIEW_NAME = "";
    private VerticalLayout content;

    public Home() {
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
        content.setWidth(70f, Unit.PERCENTAGE);
        content.setHeight(100f, Unit.PERCENTAGE);
        Label welcome = new Label("Witaj");
        welcome.addStyleName("welcome-label");
        TextArea information = new TextArea();
        information.setEnabled(false);
        information.setValue("Aplikacja posiada API REST które daje możliwość zapisu danych w bazie.\nZ API łączy się desktopowy klient który zapewnia informacje o czasie pracy jednostki na której jest uruchomiony.\nUmożliwia to monitorowanie czasu pracy.\nDane można wygodnie przeglądać w formie kalendarza lub listy filtrowanej szukanym okresem czasu.\nDodatkową funkcjonalnością jest wysyłane mailowo miesięczne podsumowanie czasu spędzonego przed komputerem\n\n\nUżytkownik demo\nLogin: demo\nHaslo: demo");
        information.setSizeFull();
        information.addStyleName("information");
        content.addComponent(welcome);
        content.addComponent(information);
        content.setExpandRatio(welcome,1.5f);
        content.setExpandRatio(information,9f);
    }
}
