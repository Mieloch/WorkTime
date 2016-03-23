package topworker.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by echomil on 23.03.16.
 */
public class MessagesBundle {
    private ResourceBundle resourceBundle;

    public MessagesBundle() {
        Locale l = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(new Locale("EN", "en"));
        resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public void changeBundle(Locale locale) {
        LocaleContextHolder.setLocale(locale);
        resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
    }
}
