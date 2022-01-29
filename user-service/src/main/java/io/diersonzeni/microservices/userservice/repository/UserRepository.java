package io.diersonzeni.microservices.userservice.repository;

import java.util.List;

import io.diersonzeni.microservices.userservice.dto.User;

public interface UserRepository {

	public User findByEmail(String email);

	public boolean addUser(User newUser);
	
	public List<User> getAll();

	public int countUsers();

}
