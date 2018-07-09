package com.manojbehera.emms.as.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manojbehera.emms.as.model.Address;
import com.manojbehera.emms.as.model.User;
import com.manojbehera.emms.as.service.AddressService;
import com.manojbehera.emms.as.service.UserService;
import com.manojbehera.emms.as.util.UserContextHolder;

@RestController
@RequestMapping("/v1/address")
public class AddressController {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Address addAddress(@RequestBody Address address) {
		
		String username = "manoj";
		
		return addressService.addAddressByUser(address, username);
	}
	
	@RequestMapping("/addressesByUser")
	public List<Address> getAllAddressesByUser() {
		String username = "manoj";
		
		return addressService.getAddressesByUsername(username);
	}
	
	@RequestMapping("/all")
	public List<Address> getAllAddresses() {
		return addressService.getAllAddresses();
	}
	
	@RequestMapping("/{id}")
	public Address getAddressById(@PathVariable Long id) {
		return addressService.getAddressById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Address updateAddress (@PathVariable Long id, @RequestBody Address address) throws IOException {
		address.setId(id);
		return addressService.updateAddress(address);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteAddressById(@PathVariable Long id) throws IOException {
		addressService.deleteAddressById(id);
	}
	
	@RequestMapping("/user/{username}")
	public User getUserByUsername(@PathVariable String username) {
		
		logger.debug("AddressServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return addressService.getUserByUsername(username);
	}
}
