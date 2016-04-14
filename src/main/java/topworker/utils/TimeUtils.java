package topworker.utils;

import org.apache.commons.lang3.time.DateUtils;
import topworker.dal.entity.WorkPeriod;

import java.util.Date;

/**
 * Created by echomil on 15.03.16.
 */
public class TimeUtils {
    public static String formatTime(int minutes) {
        return "" + minutes / 60 + "h " + minutes % 60 + "m";
    }

    public static WorkPeriod roundTimeToField(WorkPeriod period, int field) {
        Date roundedStart = DateUtils.round(period.getStart(), field);
        Date roundedStop = DateUtils.round(period.getStop(), field);
        period.setStart(roundedStart);
        period.setStop(roundedStop);
        return period;
    }
}
