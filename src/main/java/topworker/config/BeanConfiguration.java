package topworker.config;

import org.jdal.vaadin.beans.VaadinScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import topworker.TopWorkerApplication;
import topworker.service.WorkPeriodService;
import topworker.service.impl.WorkPeriodServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Echomil on 2016-02-26.
 */

@Configuration
public class BeanConfiguration {

    @Bean
    public WorkPeriodService getWorkPeriodService() {
        return new WorkPeriodServiceImpl();
    }

}
