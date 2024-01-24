package com.koushik.blog.security;

import lombok.Data;

@Data
public class JwtRequest {

	private String username;
	private String password;
}
