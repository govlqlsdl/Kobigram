package com.kobi.kobigram.like.service;

import org.springframework.stereotype.Service;

import com.kobi.kobigram.like.domain.Like;
import com.kobi.kobigram.like.repository.LikeRepository;

import jakarta.persistence.PersistenceException;

@Service
public class LikeService {
	
	private final LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public boolean addLike(int postId, int userId) {
		
		Like like = Like.builder()
		.postId(postId)
		.userId(userId)
		.build();
		
		try {			
			likeRepository.save(like);
		} catch(PersistenceException e) {
			return false;
		}
		
		return true;
		
	}
	
	// 게시글 별로 좋아요 개수 얻어오기
	public int getLikeCount(int postId) {
		return likeRepository.countByPostId(postId);
	}
	
	public boolean isLikeByPostIdAndUserId(int postId, int userId) {
		return likeRepository.existsByPostIdAndUserId(postId, userId);
	}

}