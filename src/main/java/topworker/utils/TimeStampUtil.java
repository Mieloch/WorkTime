package topworker.utils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import topWorker.restClient.model.StampType;
import topworker.model.WorkPeriod;
import topworker.model.entity.TimeStamp;
import topworker.utils.persistance.TimeStampDao;

@Component
public class TimeStampUtil {
	@Autowired
	private TimeStampDao timeStampDao;

	public TimeStampUtil() {
		// test();
	}

	@PostConstruct
	public void test() {
		getWorkingHours();
	}

	public void getWorkingHours() {
		List<TimeStamp> l = timeStampDao.getAll();
		List<WorkPeriod> workPeriods = new ArrayList<WorkPeriod>();
		WorkPeriod peroid = new WorkPeriod();

		peroid.setStart(l.get(0).getTime());
		GregorianCalendar g1 = new GregorianCalendar();
		GregorianCalendar g2 = new GregorianCalendar();
		g1.setTime(peroid.getStart());
		for (int i = 1; i < l.size(); i++) {
			TimeStamp stamp = l.get(i);
			g2.setTime(stamp.getTime());
			if (g1.get(GregorianCalendar.DAY_OF_YEAR) != g2.get(GregorianCalendar.DAY_OF_YEAR)) {
				peroid.setEnd(l.get(i - 1).getTime());
				peroid = new WorkPeriod();
				peroid.setStart(g2.getTime());
			}
			if (stamp.getStampType().equals(StampType.SHUTDOWN)) {
				peroid.setEnd(stamp.getTime());
				workPeriods.add(peroid);
				if (i + 1 < l.size()) {
					peroid = new WorkPeriod();
					peroid.setStart(l.get(i + 1).getTime());
					continue;
				}
			}
		}
		System.out.println("break");
	}

}
