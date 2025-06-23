package com.example.di.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.di.entities.User;
import com.example.di.entities.UserInRole;

@Repository
public interface UserInRoleRepository extends CrudRepository<UserInRole, Integer>{

	@Query("SELECT u.user FROM UserInRole u WHERE u.role.name=?1")
	public List<User> findUsersByRoleName(String roleName);
	
	public List<UserInRole> findByUser(User user);
}
