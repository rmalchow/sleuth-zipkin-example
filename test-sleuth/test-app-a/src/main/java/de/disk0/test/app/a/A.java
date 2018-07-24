package de.disk0.test.app.a;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude={org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class,org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class})
@ComponentScan(basePackages={"de.disk0"})
public class A {
	
	@Value("${server.port}")
	private int serverPort;
	
	@PostConstruct
	public void init() {
	}

	public static void main(String[] args) {
	    SpringApplication.run(A.class, args);
	}
	
	
}
