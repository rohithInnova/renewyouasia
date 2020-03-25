package com.rya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rya.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	
}
