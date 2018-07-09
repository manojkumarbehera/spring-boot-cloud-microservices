package com.manojbehera.emms.as.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.manojbehera.emms.as.model.Address;
import com.manojbehera.emms.as.model.User;

@Transactional
public interface AddressRepository extends CrudRepository<Address, Long> {

	List<Address> findByUser(User user);

	Address findByCity(String city);
}
