package com.kobi.kobigram.post.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kobi.kobigram.post.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// WHERE `postId` = #{}
	public List<Comment> findByPostId(int postId);
}