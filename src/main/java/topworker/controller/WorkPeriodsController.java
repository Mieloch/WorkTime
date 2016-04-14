package topworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import topworker.controller.transport.PostPeriodMessage;
import topworker.dal.entity.WorkPeriod;
import topworker.service.WorkPeriodService;

@RestController
@RequestMapping("/api/")
public class WorkPeriodsController {

    @Autowired
    private WorkPeriodService workPeriodService;

    @RequestMapping(value = "/workperiod", method = RequestMethod.POST)
    public HttpStatus postPeriod(@RequestBody PostPeriodMessage postPeriodMessage) {
        try {
            WorkPeriod workPeriod = new WorkPeriod();
            workPeriod.setStart(postPeriodMessage.getStart());
            workPeriod.setStop(postPeriodMessage.getEnd());
            workPeriodService.postTimeToUser(postPeriodMessage.getLogin(), workPeriod);
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
