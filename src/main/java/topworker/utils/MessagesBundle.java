package topworker.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

/**
 * Created by echomil on 23.03.16.
 */
public class MessagesBundle {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());

    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
