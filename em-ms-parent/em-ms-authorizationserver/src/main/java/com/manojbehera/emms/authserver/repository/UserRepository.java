package com.manojbehera.emms.authserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.manojbehera.emms.authserver.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
}
