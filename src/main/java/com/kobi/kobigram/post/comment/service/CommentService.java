package com.kobi.kobigram.post.comment.service;

import org.springframework.stereotype.Service;

import com.kobi.kobigram.post.comment.domain.Comment;
import com.kobi.kobigram.post.comment.repository.CommentRepository;

import jakarta.persistence.PersistenceException;

@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public boolean addComment(int postId, int userId, String contents) {
		
		
		Comment comment = Comment.builder()
		.postId(postId)
		.userId(userId)
		.contents(contents)
		.build();
		
		try {
			commentRepository.save(comment);
			
		} catch(PersistenceException e) {
			return false;
		}
		
		return true;
	}

}
