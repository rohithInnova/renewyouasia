package com.rya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rya.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	

}
