package com.kobi.kobigram.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kobi.kobigram.post.dto.CardView;
import com.kobi.kobigram.post.service.PostService;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/timeline-view")
	public String timeline(Model model) {
		
		List<CardView> cardList = postService.getPostList();
		
		model.addAttribute("cardList", cardList);
		
		return "post/timeline";
	}
	
	 @GetMapping("/create-view")
	    public String createPostView() {
	        return "post/create";
    }
}

