package com.zeeshan.multiple.ds.api.user.service;

import com.zeeshan.multiple.ds.api.model.user.User;
import com.zeeshan.multiple.ds.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@EnableTransactionManagement
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	 @Transactional
	public User addUser(User user) {
		return userRepository.save(user);
	}
}
