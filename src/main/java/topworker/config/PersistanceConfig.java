package topworker.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties
@ComponentScan
public class PersistanceConfig {

    @Resource(name = "hibernateProperties")
    Properties properties;

/*    @Bean(name = "LocalContainerEntityManagerFactoryBean")
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(javax.sql.DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[]{"topworker.dal"});
       // em.setJpaDialect(new HibernateJpaDialect());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       em.setJpaProperties(properties);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;

    }*/

    @Bean(name = "PlatformTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean(name = "PersistenceExceptionTranslationPostProcessor")
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
