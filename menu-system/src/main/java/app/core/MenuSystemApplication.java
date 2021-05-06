package app.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.core.services.CreateDatabaseService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class MenuSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuSystemApplication.class, args);
	}
	
	//@Bean
	CommandLineRunner initDBWithData(CreateDatabaseService cds) {
		CommandLineRunner runner = new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				cds.createDatabase();
			}
		};
		return runner;
	}

}
