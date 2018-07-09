package com.manojbehera.emms.es.service;

import com.manojbehera.emms.es.model.User;

public interface UserService {

	User createUser(User user);

	User getUserByUserName(String userName);

}
