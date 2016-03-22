package topworker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import topworker.view.utils.AuthenticationService;

/**
 * Created by Echomil on 2016-02-26.
 */

@Configuration
public class BeanConfiguration {

    @Bean
    @Scope(scopeName = "prototype")
    public AuthenticationService getAuthenticationService() {
        return new AuthenticationService();
    }
}
