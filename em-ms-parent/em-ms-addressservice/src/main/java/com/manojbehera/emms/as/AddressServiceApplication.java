package com.manojbehera.emms.as;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.manojbehera.emms.as.model.Address;
import com.manojbehera.emms.as.model.User;
import com.manojbehera.emms.as.service.AddressService;
import com.manojbehera.emms.as.service.UserService;

@SpringBootApplication
// @RefreshScope
@EnableFeignClients
@EnableEurekaClient
@EnableResourceServer
@EnableCircuitBreaker
public class AddressServiceApplication implements CommandLineRunner {

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserService userService;

	@Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}
	
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}

	public static void main(String[] args) {
		SpringApplication.run(AddressServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userService.findByUsername("manoj");

		Address address1 = new Address();
		address1.setCity("Bangalore");
		address1.setState("Karnataka");
		address1.setAddDate(new Date());
		address1.setUser(user);

		addressService.addAddressByUser(address1, user.getUsername());

		Address address2 = new Address();
		address2.setCity("Bangalore");
		address2.setState("Karnataka");
		address2.setAddDate(new Date());
		address2.setUser(user);

		addressService.addAddressByUser(address2, user.getUsername());
	}
}
