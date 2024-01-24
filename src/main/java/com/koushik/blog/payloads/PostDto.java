package com.koushik.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

	
	private String title;
	private String content;
	private String imageName;
	private Date addeddate;
	
	private CategoryDto category;
	
	private UserDto user;
	private Set<Commentdto> comment=new HashSet<>();
}
