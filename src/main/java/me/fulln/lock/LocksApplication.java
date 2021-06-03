package me.fulln.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class LocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocksApplication.class, args);
	}

}
