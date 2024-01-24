package com.koushik.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.koushik.blog.config.AppConstants;
import com.koushik.blog.entities.Role;
import com.koushik.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApisApplication.class, args);
	}

	@Bean
    ModelMapper modelMapper() {
	   return new ModelMapper();
   }

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			this.roleRepo.saveAll(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
