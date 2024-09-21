package com.argjerryl.the_banking_application;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "The Banking Application",
				description = "Backend Rest APIs for JA Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Jerry Arguello",
						email = "jla44@njit.edu",
						url = "#"
				),
				license = @License(
						name = "The Banking Application",
						url = "#"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "The Banking Application SpringBoot Fundamentals",
				url = "#"
		)
	)
//http://localhost:8080/swagger-ui/index.html#/

public class TheBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheBankingApplication.class, args);
	}

}
