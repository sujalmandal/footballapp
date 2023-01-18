package s.m.learning.footballapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FootballApp {

	public static void main(String[] args) {
		SpringApplication.run(FootballApp.class, args);
	}

}
