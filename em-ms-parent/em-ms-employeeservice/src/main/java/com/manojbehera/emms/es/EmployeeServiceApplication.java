package com.manojbehera.emms.es;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.manojbehera.emms.es.model.Role;
import com.manojbehera.emms.es.model.User;
import com.manojbehera.emms.es.model.UserRole;
import com.manojbehera.emms.es.service.UserService;

@SpringBootApplication
//@RefreshScope
@EnableEurekaClient
@EnableResourceServer
public class EmployeeServiceApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}

	public static void main(String[] args) {

		SpringApplication.run(EmployeeServiceApplication.class, args);

	}

	@Override
	public void run(String... arg0) throws Exception {

		User user1 = new User();
		user1.setFirstName("Manoj");
		user1.setLastName("Behera");
		user1.setUsername("manoj");
		user1.setPassword("password");
		user1.setEmail("manoj@gmail.com");

		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1);

	}
}
