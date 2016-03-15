package topworker.utils;

/**
 * Created by echomil on 15.03.16.
 */
public class TimeUtils {
    public static String formatTime(int minutes) {
        return "" + minutes / 60 + "h " + minutes % 60 + "m";
    }

}
