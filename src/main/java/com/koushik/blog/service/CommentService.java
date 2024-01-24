package com.koushik.blog.service;

import com.koushik.blog.payloads.Commentdto;

public interface CommentService {

    
	Commentdto createComment(Commentdto commentdto,Integer postId);
	
	void deleteCpmment(Integer commentId);
}
