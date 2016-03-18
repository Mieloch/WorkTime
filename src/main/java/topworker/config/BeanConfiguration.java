package topworker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import topworker.utils.DesEncrypter;

/**
 * Created by Echomil on 2016-02-26.
 */

@Configuration
public class BeanConfiguration {

    @Bean(name = "DesEncrypter")
    public DesEncrypter getDesEncrypter() {
        return new DesEncrypter();
    }
}
