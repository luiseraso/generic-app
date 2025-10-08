package com.example.di;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.di.entities.Role;
import com.example.di.entities.User;
import com.example.di.entities.UserInRole;
import com.example.di.repositories.RoleRepository;
import com.example.di.repositories.UserInRoleRepository;
import com.example.di.repositories.UserRepository;
import com.github.javafaker.Faker;

@SpringBootApplication
public class Application implements ApplicationRunner{

	@Autowired
	private Faker faker;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roles[] = {new Role("ADMIN"), new Role("SUPPORT"), new Role("USER")};
		
		for(Role role : roles) {
			roleRepository.save(role);
		}
		
		for(int i=0; i<10; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			User created = repository.save(user);
			UserInRole userInRole = new UserInRole(created, roles[new Random().nextInt(3)]);
			log.info("User created username {} password {} role {} ", created.getUsername(),created.getPassword(),userInRole.getRole().getName());
			userInRoleRepository.save(userInRole);
		}
	}

}
