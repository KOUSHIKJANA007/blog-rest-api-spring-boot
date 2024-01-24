package com.koushik.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.koushik.blog.entities.Category;
import com.koushik.blog.entities.Post;
import com.koushik.blog.entities.User;
import com.koushik.blog.exception.ResourceNotFoundException;
import com.koushik.blog.payloads.PostDto;
import com.koushik.blog.payloads.PostResponse;
import com.koushik.blog.repositories.CategoryRepo;
import com.koushik.blog.repositories.PostRepo;
import com.koushik.blog.repositories.UserRepo;
import com.koushik.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer catId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userID", userId));
		Category category=this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryID", catId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.jpeg");
		post.setAddeddate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post savepost = this.postRepo.save(post);
		return this.modelMapper.map(savepost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post savepost = this.postRepo.save(post);
		return this.modelMapper.map(savepost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));;
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagepost = this.postRepo.findAll(p);
		List<Post> allposts = pagepost.getContent();
		List<PostDto> collectposts = allposts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(collectposts);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastPage(pagepost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer catId) {
		Category cat=this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", catId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> collectpost = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collectpost;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> collectposts = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collectposts;
	}

	@Override
	public List<PostDto> serachPost(String keyword) {
		List<Post> searchByTitle = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> collect = searchByTitle.stream().map((p)->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

}
