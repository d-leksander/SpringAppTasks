package io.Daro.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.validation.Validator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@EnableAsync
@SpringBootApplication
public class AppTasksApplication {


	public static void main(String[] args) {

		SpringApplication.run(AppTasksApplication.class, args);
	}


	@Bean
	Validator validator() {
		return  new LocalValidatorFactoryBean();
	}

}
