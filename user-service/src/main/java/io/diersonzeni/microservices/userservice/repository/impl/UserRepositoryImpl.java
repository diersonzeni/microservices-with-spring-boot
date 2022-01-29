package io.diersonzeni.microservices.userservice.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import io.diersonzeni.microservices.userservice.dto.User;
import io.diersonzeni.microservices.userservice.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	private static Set<User> users = new HashSet<User>();
	
	@Override
	public User findByEmail(String email) {
		for (User user : users) {
			if(user.getEmail().equals(email)) {
				return user;
			}
		}

		return null;
	}

	@Override
	public int countUsers() {
		return users.size();
	}

	@Override
	public boolean addUser(User newUser) {
		return users.add(newUser);
	}

	@Override
	public List<User> getAll() {
		return new ArrayList<>(users);
	}

}
