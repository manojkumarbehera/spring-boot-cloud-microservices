package com.manojbehera.emms.as.service;

import com.manojbehera.emms.as.model.User;

public interface UserService {
	
	User findByUsername(String username);
}
