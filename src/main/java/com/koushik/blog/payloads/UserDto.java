package com.koushik.blog.payloads;

import java.util.HashSet;
import java.util.Set;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 3,max = 12)
	private String name;
	
	@Email
	private String email;
	
	@NotNull
	@Size(min = 3,max = 8)
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> role = new HashSet<>();
}

