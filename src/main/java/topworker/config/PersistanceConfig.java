package topworker.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@ConfigurationProperties(locations = "classpath:database.properties")
public class PersistanceConfig {

    @Resource(name = "hibernateProperties")
    private Properties hibernateProperties;

    @Bean(name = "dataBase")
    public DataSource getDataSource() {
        DataSource dataSource = new DataSource();
        return dataSource;

    }

    @Bean(name = "LocalContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[]{"topworker.model.dal"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties);

        return em;

    }

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
