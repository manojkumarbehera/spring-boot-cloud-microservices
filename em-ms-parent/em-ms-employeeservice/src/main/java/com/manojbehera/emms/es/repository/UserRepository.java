package com.manojbehera.emms.es.repository;

import org.springframework.data.repository.CrudRepository;

import com.manojbehera.emms.es.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
}
