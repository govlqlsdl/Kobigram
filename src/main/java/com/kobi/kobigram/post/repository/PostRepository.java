package com.kobi.kobigram.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kobi.kobigram.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
