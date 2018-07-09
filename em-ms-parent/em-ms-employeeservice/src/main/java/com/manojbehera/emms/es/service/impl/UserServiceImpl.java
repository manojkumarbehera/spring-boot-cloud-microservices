package com.manojbehera.emms.es.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manojbehera.emms.es.model.Role;
import com.manojbehera.emms.es.model.User;
import com.manojbehera.emms.es.model.UserRole;
import com.manojbehera.emms.es.repository.UserRepository;
import com.manojbehera.emms.es.service.UserService;
import com.manojbehera.emms.es.utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {
		
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if (localUser != null) {
			LOG.info("user with username {} already exist...." + user.getUsername());
		} else {
			
			Set<UserRole> userRoles = new HashSet<>();
			Role localRole = new Role();
			localRole.setRoleId(1);
			userRoles.add(new UserRole(user, localRole));
			user.getUserRoles().addAll(userRoles);
			
			Date today = new Date();
			user.setJoinDate(today);
			
			String encypteted = SecurityUtility.passwordEncoder().encode(user.getPassword());
			user.setPassword(encypteted);
			
			localUser = userRepository.save(user);
			
		}
		
		return localUser;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

}
