package com.kobi.kobigram.post.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kobi.kobigram.post.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}