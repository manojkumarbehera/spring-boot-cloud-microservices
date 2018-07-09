package com.manojbehera.emms.as.repository;

import org.springframework.data.repository.CrudRepository;

import com.manojbehera.emms.as.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
}
