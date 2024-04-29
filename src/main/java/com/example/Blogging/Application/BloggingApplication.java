package com.example.Blogging.Application;

import com.example.Blogging.Application.Config.AppConstants;
import com.example.Blogging.Application.Entities.Role;
import com.example.Blogging.Application.Repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean      // Spring Container will automatically create an object of this and provide us, where we autowired it
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(this.passwordEncoder.encode("xyz"));
//	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role(AppConstants.ADMIN_USER , "ROLE_ADMIN");
			roleRepository.save(role1);

			Role role2 = new Role(AppConstants.NORMAL_USER , "ROLE_NORMAL");
			roleRepository.save(role2);

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
