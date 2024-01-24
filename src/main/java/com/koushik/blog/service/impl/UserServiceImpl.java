package com.koushik.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koushik.blog.config.AppConstants;
import com.koushik.blog.entities.Role;
import com.koushik.blog.entities.User;
import com.koushik.blog.payloads.UserDto;
import com.koushik.blog.repositories.RoleRepo;
import com.koushik.blog.repositories.UserRepo;
import com.koushik.blog.service.UserService;
import com.koushik.blog.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User saveuser = repo.save(user);
		return this.UserToDto(saveuser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.repo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		User upadteduser = this.repo.save(user);
		UserDto userToDto = this.UserToDto(upadteduser);
		return userToDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.repo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.repo.findAll();
		List<UserDto> usersDto = users.stream().map(user->UserToDto(user)).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user=this.repo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		this.repo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto) {
		User u=this.modelMapper.map(userDto,User.class);
		return u;
	}
	
	public UserDto UserToDto(User u) {
		UserDto dto=this.modelMapper.map(u, UserDto.class);
		return dto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRole().add(role);
		User newuser = this.repo.save(user);
		return this.modelMapper.map(newuser, UserDto.class);
	}

}
