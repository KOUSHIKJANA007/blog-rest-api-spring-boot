package com.koushik.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koushik.blog.payloads.ApiResponse;
import com.koushik.blog.payloads.Commentdto;
import com.koushik.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<Commentdto> createComments(@RequestBody Commentdto comment ,@PathVariable("postId") Integer postId){
		Commentdto createComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<Commentdto>(createComment,HttpStatus.OK);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteCpmment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("delete successfully...",true),HttpStatus.OK);
	}
}
