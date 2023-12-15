package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

@OpenAPIDefinition(
		info = @Info(
				title = "Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentations",
				version = "v1.0",
				contact = @Contact(
						email = "nehamete21@gmail.com",
						name = "neha mete"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot blog application documentation",
				url="provide github link here"
		)
)
public class BlogRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}
    @Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {


		if( roleRepository.count() == 0) {
			Role adminrole = new Role();
			adminrole.setName("ROLE_ADMIN");

			roleRepository.save(adminrole);

			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);
		}

	}
}
