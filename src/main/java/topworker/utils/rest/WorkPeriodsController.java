package topworker.utils.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import topworker.model.WorkPeriodService;
import topworker.model.bo.WorkPeriod;

@RestController
@RequestMapping("/workperiods/")
public class WorkPeriodsController {

	@Autowired
	private WorkPeriodService workPeriodService;

	Logger log = Logger.getLogger(WorkPeriodsController.class.getName());
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<WorkPeriod> postPeriod(@RequestBody WorkPeriod period) {
		System.out.println("Posting workPeriod");
		System.out.println("PRZ POSCIE " + period.getStop());
		workPeriodService.postTime(period);

		log.log(Level.INFO, "Posting workPeriod");
		return new ResponseEntity<WorkPeriod>(HttpStatus.OK);
	}

	public WorkPeriodService getWorkPeriodService() {
		return workPeriodService;
	}

	public void setWorkPeriodService(WorkPeriodService workPeriodService) {
		this.workPeriodService = workPeriodService;
	}
}
