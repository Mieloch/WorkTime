package topworker.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import topworker.utils.MessagesBundle;

/**
 * Created by Echomil on 2016-02-26.
 */

@Configuration
public class BeanConfiguration {


    @Bean(name = "apiProperties")
    public PropertiesFactoryBean apiProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("api.properties"));
        return propertiesFactoryBean;
    }

    @Bean(name = "hibernateProperties")
    public PropertiesFactoryBean hibernateProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("hibernate.properties"));
        return propertiesFactoryBean;
    }

    @Bean
    @Scope(scopeName = "session")
    public MessagesBundle getMessagesBundle() {
        return new MessagesBundle();
    }
}
