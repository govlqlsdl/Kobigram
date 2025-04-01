package com.kobi.kobigram.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kobi.kobigram.like.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// SELECT count(*) FROM `like` WHERE `postId` = #{}
	public int countByPostId(int postId);
	
	// 존재여부
	public boolean existsByPostIdAndUserId(int postId, int userId);

}