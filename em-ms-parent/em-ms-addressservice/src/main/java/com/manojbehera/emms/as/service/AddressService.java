package com.manojbehera.emms.as.service;

import java.io.IOException;
import java.util.List;

import com.manojbehera.emms.as.model.Address;
import com.manojbehera.emms.as.model.User;

public interface AddressService {
	
	Address addAddressByUser(Address address, String username);

	List<Address> getAllAddresses();

	List<Address> getAddressesByUsername(String username);

	Address getAddressById(Long id);

	Address updateAddress(Address address) throws IOException;

	void deleteAddressById(Long id);

	User getUserByUsername(String username);
}
