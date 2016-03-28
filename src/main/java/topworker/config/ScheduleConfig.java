package topworker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by echomil on 26.03.16.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ScheduleConfig {

    @Bean
    public TaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(3);
        executor.setThreadNamePrefix("EXECUTOR");
        return executor;
    }
}
