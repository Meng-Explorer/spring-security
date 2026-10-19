package com.security_asymmetric_encryption;

import com.security_asymmetric_encryption.role.Role;
import com.security_asymmetric_encryption.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
//@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final RoleRepository roleRepository) {

		return args -> {
			final Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
			if (userRole.isEmpty()) {
				final Role role = new Role();
				role.setName("ROLE_USER");
				role.setCreatedBy("APP");
				roleRepository.save(role);
				System.out.println(">>> ROLE_USER has been created successfully!");
			}
		};


	}

}
