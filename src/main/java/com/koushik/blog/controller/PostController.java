package com.koushik.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.koushik.blog.config.AppConstants;
import com.koushik.blog.payloads.ApiResponse;
import com.koushik.blog.payloads.PostDto;
import com.koushik.blog.payloads.PostResponse;
import com.koushik.blog.service.FileService;
import com.koushik.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable("userId") Integer userId,@PathVariable("catId") Integer catId){
		PostDto createPost = this.postService.createPost(postDto, userId, catId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@RequestBody @PathVariable("userId") Integer userId){
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return ResponseEntity.ok(postByUser);
	}
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@RequestBody @PathVariable("catId") Integer catId){
		List<PostDto> postByCategory = this.postService.getPostByCategory(catId);
		return ResponseEntity.ok(postByCategory);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
	                                                  @RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required=false) Integer pageSize,
	                                                  @RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY ,required = false) String sortBy,
	                                                  @RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR ,required = false) String sortDir
			                                        ) {
		 PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@RequestBody @PathVariable("postId") Integer postId){
		PostDto postById = this.postService.getPostById(postId);
		return ResponseEntity.ok(postById);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Successfully deleted",true));
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatePost);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword){
		List<PostDto> serachPost = this.postService.serachPost(keyword);
		return new ResponseEntity<List<PostDto>>(serachPost,HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String uploadImage = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(uploadImage);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping(value = "/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		InputStream resource=this.fileService.getResources(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
}

