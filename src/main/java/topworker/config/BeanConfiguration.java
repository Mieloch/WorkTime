package topworker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import topworker.model.service.WorkPeriodService;
import topworker.model.service.impl.WorkPeriodServiceImpl;

/**
 * Created by Echomil on 2016-02-26.
 */

@Configuration
public class BeanConfiguration {

    @Bean
    public WorkPeriodService getWorkPeriodService(){
        return new WorkPeriodServiceImpl();
    }
}
