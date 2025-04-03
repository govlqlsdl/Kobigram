package com.kobi.kobigram.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kobi.kobigram.common.FileManager;
import com.kobi.kobigram.like.service.LikeService;
import com.kobi.kobigram.post.comment.dto.CommentView;
import com.kobi.kobigram.post.comment.service.CommentService;
import com.kobi.kobigram.post.domain.Post;
import com.kobi.kobigram.post.dto.CardView;
import com.kobi.kobigram.post.repository.PostRepository;
import com.kobi.kobigram.user.domain.User;
import com.kobi.kobigram.user.service.UserService;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 부여된 멤버 변수만 파라미터로 전달받아서 채워준다(@Autowired 를 자동으로 해주는것이 아님, 생성자를 만들어주는것)
@Service
public class PostService {
	
	// 원래는 @Autowired 를 통해서 객체를 얻어오고 그걸 생성자를 통해 하는것이 정석
	private final PostRepository postRepository;
	
	private final UserService userService;
	
	private final LikeService likeService;
	
	private final CommentService commentService;
	
	public boolean addPost(int userId, String contents, MultipartFile imageFile) {
		
		String imagePath = FileManager.saveFile(userId, imageFile);
		
		Post post = Post.builder()
		.userId(userId)
		.contents(contents)
		.imagePath(imagePath)
		.build();
		
		try {			
			postRepository.save(post);
		} catch(PersistenceException e) {
			return false;
		}
		
		return true;
		
	}
	
	public List<CardView> getPostList(int userId) {
		// 최신 업로드 기준으로 정렬
		List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		List<CardView> cardList = new ArrayList<>();
		for(Post post:postList) {
			
			User user = userService.getUserById(post.getUserId());
			
			int likeCount = likeService.getLikeCount(post.getId());
			
			boolean isLike = likeService.isLikeByPostIdAndUserId(post.getId(), userId);
			
			List<CommentView> commentList = commentService.getCommentList(post.getId());
			
			CardView cardView = CardView.builder()
			.postId(post.getId())
			.contents(post.getContents())
			.imagePath(post.getImagePath())
			.userId(post.getUserId())
			.loginId(user.getLoginId())
			.likeCount(likeCount)
			.isLike(isLike)
			.commentList(commentList)
			.build();
			
			cardList.add(cardView);
		}
		
		return cardList;
		
	}
	
	public boolean deletePost(int id, int userId) {
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			// 삭제 대상 게시글 정보의 작성자와 로그인한 사용자가 일치하지 않는 경우
			// 삭제 실패
			if(post.getUserId() != userId) {
				return false;
			}
			
			FileManager.removeFile(post.getImagePath());
			
			likeService.deleteLikeByPostId(post.getId());
			commentService.deleteCommentByPostId(post.getId());
			
			try {
				postRepository.delete(post);
			} catch(PersistenceException e) {
				return false;
			}
			
		}else {
			return false;
		}
		return true;
	}
	
	
}