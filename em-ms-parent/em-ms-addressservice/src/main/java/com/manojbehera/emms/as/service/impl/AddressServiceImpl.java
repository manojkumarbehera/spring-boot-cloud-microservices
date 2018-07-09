package com.manojbehera.emms.as.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manojbehera.emms.as.client.UserFeignClient;
import com.manojbehera.emms.as.client.UserRestTemplateClient;
import com.manojbehera.emms.as.model.Address;
import com.manojbehera.emms.as.model.User;
import com.manojbehera.emms.as.repository.AddressRepository;
import com.manojbehera.emms.as.service.AddressService;
import com.manojbehera.emms.as.service.UserService;
import com.manojbehera.emms.as.util.UserContextHolder;

@Service
public class AddressServiceImpl implements AddressService {

	private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private UserRestTemplateClient userRestTemplateClient;

	@Override
	public Address addAddressByUser(Address address, String username) {
		Address localAddress = addressRepository.findByCity(address.getCity());

		if (localAddress != null) {
			LOG.info("address with name {} already exists. Nothing will be done.", address.getCity());
			return null;
		} else {
			Date today = new Date();
			address.setAddDate(today);

			User user = userService.findByUsername(username);
			address.setUser(user);
			Address newAddress = addressRepository.save(address);

			return newAddress;
		}
	}

	@Override
	public List<Address> getAllAddresses() {

		return (List<Address>) addressRepository.findAll();
	}

	@Override
	public List<Address> getAddressesByUsername(String username) {

		User user = userService.findByUsername(username);

		return (List<Address>) addressRepository.findByUser(user);
	}

	@Override
	public Address getAddressById(Long id) {
		return addressRepository.findOne(id);
	}

	@Override
	public Address updateAddress(Address address) throws IOException {

		Address localAddress = getAddressById(address.getId());

		if (localAddress == null) {
			throw new IOException("Address was not found.");
		} else {
			localAddress.setCity(address.getCity());
			localAddress.setState(address.getState());

			return addressRepository.save(localAddress);
		}
	}

	@Override
	public void deleteAddressById(Long id) {
		addressRepository.delete(id);
	}

	@Override
	/* @HystrixCommand(commandProperties = {
	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
	value = "12000") })*/
	/*@HystrixCommand(fallbackMethod = "buildFallbackUser", threadPoolKey = "addressByUserThreadPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			 (requests per second at peak when the service is healthy * 99th percentile
			latency in seconds) + small amount of extra threads for overhead
			@HystrixProperty(name = "maxQueueSize", value = "10") })*/
	public User getUserByUsername(String username) {
		// return userService.findByUsername(username);

		//randomlyRunLong();
		LOG.debug("addressService.getUserByUsername Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		//return userFeignClient.getUserByUsername(username);
		return userRestTemplateClient.getUser(username);
	}

	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if (randomNum == 3)
			sleep();
	}

	private void sleep() {
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private User buildFallbackUser(String username) {

		User user = new User();
		user.setId(123123L);
		user.setUsername("Temp Username");

		return user;
	}
}
