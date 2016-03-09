package topworker.view.home;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.boot.test.SpringApplicationConfiguration;
import topworker.view.login.Login;

/**
 * Created by echomil on 07.03.16.
 */
@SpringView(name = Home.VIEW_NAME)
public class Home extends VerticalLayout implements View {

    public final static String VIEW_NAME = "";
    private VerticalLayout content;

    public Home(){
        init();
        setSizeFull();
        addComponent(content);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    public void init(){
        content = new VerticalLayout();
        content.setSizeFull();
        Label welcome = new Label("Witaj");
        TextArea information = new TextArea();
        information.setValue("Aplikacja posiada API REST które daje możliwość zapisu danych w bazie.\nZ API łączy się desktopowy klient który zapewnia informacje o czasie pracy jednostki na której jest uruchomiony.\nUmożliwia to monitorowanie czasu pracy.\nDane można wygodnie przeglądać w formie kalendarza lub listy filtrowanej szukanym okresem czasu.\nDodatkową funkcjonalnością jest wysyłane mailowo miesięczne podsumowanie czasu spędzonego przed komputerem\n\n\nUżytkownik demo\nLogin: demo\nHaslo: demo");
        information.setSizeFull();
        content.addComponent(welcome);
        content.addComponent(information);
    }
}
