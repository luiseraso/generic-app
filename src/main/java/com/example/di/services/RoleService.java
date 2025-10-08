package com.example.di.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.di.entities.Role;
import com.example.di.entities.User;
import com.example.di.models.AuditDetails;
import com.example.di.models.Devs4jSecurityRule;
import com.example.di.repositories.RoleRepository;
import com.example.di.repositories.UserInRoleRepository;

@Service
@Devs4jSecurityRule
public class RoleService {
	
	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private UserInRoleRepository inRoleRepository;

	@Autowired
	private EventService eventService;
	
	private static final Logger log = LoggerFactory.getLogger(RoleService.class);
	
	public List<User> getUsersByRole(String roleName){
		log.info("Getting roles by name {}", roleName);
		return inRoleRepository.findUsersByRoleName(roleName);
	}
	
	@Cacheable("roles")
	public List<Role> getRole(){
		log.info("Getting roles");
		return repository.findAll();
	}
	
	public Role createRole(Role role) {
		Role roleCreated = repository.save(role);
		AuditDetails details = new AuditDetails(SecurityContextHolder.getContext().getAuthentication().getName(), role.getName());
		eventService.sendAuditDetails(details);
		return roleCreated;
	}
	
	public Role updateRole(Integer roleId, Role role) {
		Optional<Role> result = repository.findById(roleId);
		if(result.isPresent()) {
			return repository.save(role);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn´t exists", roleId));
		}
	}

	public void deleteRole(Integer roleId) {
		Optional<Role> result = repository.findById(roleId);
		
		if(result.isPresent()) {
			repository.delete(result.get());
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn´t exists", roleId));
		}
	}

}
