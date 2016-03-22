package topworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import topworker.service.impl.EncryptionServiceImpl;

import java.util.Arrays;

@Configuration
@EnableAutoConfiguration
@ComponentScan

@SpringBootApplication
public class TopWorkerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TopWorkerApplication.class);
	}

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(TopWorkerApplication.class, args);
		printBeanNames(ctx);
		EncryptionServiceImpl encryptionService = (EncryptionServiceImpl) ctx.getBean("encryptionServiceImpl");
		encryptionService.init();
	}



	private static void printBeanNames(ApplicationContext ctx) {
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
