package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.core.services.CreateDatabaseService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CreateDatabaseApp {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CreateDatabaseApp.class, args);
		CreateDatabaseService cds = ctx.getBean(CreateDatabaseService.class);
		cds.createDatabase();
		
	}
}
