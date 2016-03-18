package topworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import topworker.model.bo.WorkPeriod;
import topworker.service.WorkPeriodService;

@RestController
@RequestMapping("/worktime/")
public class WorkPeriodsController {

    @Autowired
    private WorkPeriodService workPeriodService;

    @RequestMapping(value = "/workperiod", method = RequestMethod.POST)
    public ResponseEntity<WorkPeriod> postPeriod(@RequestBody WorkPeriod period) {
        try {
            workPeriodService.postTime(period);
        } catch (Exception e) {
            return new ResponseEntity<WorkPeriod>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<WorkPeriod>(HttpStatus.OK);
    }

    public WorkPeriodService getWorkPeriodService() {
        return workPeriodService;
    }

    public void setWorkPeriodService(WorkPeriodService workPeriodService) {
        this.workPeriodService = workPeriodService;
    }
}
