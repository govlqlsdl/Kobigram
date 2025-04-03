package com.kobi.kobigram.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kobi.kobigram.like.domain.Like;

import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// SELECT count(*) FROM `like` WHERE `postId` = #{}
	public int countByPostId(int postId);
	
	// 존재여부
	public boolean existsByPostIdAndUserId(int postId, int userId);

	// return될 값은 하나일것이고 리스트가 아니긴 하지만 엔티티클래스 그대로 리턴하지는X
	// jpa에서는 조회될 대상이 없는 경우에는 null을 리턴할것이고 그런 경우엔 null처리를 사용하는 쪽에서 명확히 할 수 있도록 옵셔널을 사용하는것을 추천
	// 실제로 findById같은 한 행을 조회하는 기능의 리턴타입이 optional이였다
	public Optional<Like> findByPostIdAndUserId(int postId, int userId);
	
	// SELECT * FROM `like` WHERE `postId` = #{} 으로 먼저 조회한다 (2번째랑 한묶음)
	// DELETE FROM `like` WHERE `postId` = #{}
	// transaction
	// Rollback : 이전 상태로 되돌린다.
	// 삭제과정에 문제가 생기는 경우 진행된 모든 과정을 되돌린다.
	@Transactional // jpa를 통해 직접 부여한 조건으로 delete메소드 구성할 때 추가해야하는 어노테이션
	public void deleteByPostId(int postId);
	
}