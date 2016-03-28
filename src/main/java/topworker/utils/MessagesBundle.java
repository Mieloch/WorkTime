package topworker.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by echomil on 23.03.16.
 */
public class MessagesBundle {
    private ResourceBundle resourceBundle;
    private Locale locale;

    public MessagesBundle() {
        locale = new Locale("EN", "en");
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public void changeBundle(Locale locale) {
        this.setLocale(locale);
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
