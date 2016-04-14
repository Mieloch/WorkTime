package topworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import topworker.model.bo.WorkPeriod;
import topworker.service.WorkPeriodService;

@RestController
@RequestMapping("/api/")
public class WorkPeriodsController {

    @Autowired
    private WorkPeriodService workPeriodService;

    @RequestMapping(value = "/workperiod", method = RequestMethod.POST)
    public HttpStatus postPeriod(@RequestBody Message msg) {
        try {
            WorkPeriod workPeriod = new WorkPeriod();
            workPeriod.setStart(msg.getStart());
            workPeriod.setStop(msg.getEnd());
            workPeriodService.postTimeToUser(msg.getLogin(), workPeriod);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    public WorkPeriodService getWorkPeriodService() {
        return workPeriodService;
    }

    public void setWorkPeriodService(WorkPeriodService workPeriodService) {
        this.workPeriodService = workPeriodService;
    }
}
