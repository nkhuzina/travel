package com.tgog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tgog.repository")
@EntityScan("com.tgog.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class TGOGApplication {
	public static void main(String[] args) {
		SpringApplication.run(TGOGApplication.class, args);
	}
}
