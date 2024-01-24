package com.koushik.blog.service;

import java.util.List;

import com.koushik.blog.payloads.PostDto;
import com.koushik.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer catId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	PostDto getPostById(Integer postId);
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	List<PostDto> getPostByCategory(Integer catId);
	List<PostDto> getPostByUser(Integer userId);
	List<PostDto> serachPost(String keyword);
}
