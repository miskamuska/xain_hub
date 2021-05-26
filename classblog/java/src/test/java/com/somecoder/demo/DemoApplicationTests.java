package com.somecoder.demo;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.somecoder.demo.blog.entity.Collect;
import com.somecoder.demo.blog.entity.dto.CountDto;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.ICommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Resource
	private PostMapper postMapper;

	@Resource
	private CommentMapper commentMapper;

	@Resource
	private StudentMapper studentMapper;

	@Resource
	private CollectMapper collectMapper;

	@Resource
	private ReplyMapper replyMapper;

	@Resource
	private UpvoteMapper upvoteMapper;
	@Resource
	private ICommentService commentService;

	@Test
	void contextLoads() {
		List<String> list = new ArrayList<>(2);
		list.add("post-aaa48699-ffec-4de4-93aa-827fc61cd544");
		list.add("post-057cab56-0250-413e-ab5c-cc64110cfac5");
		List<Collect> collects = collectMapper.selectList(
				Wrappers.lambdaQuery(Collect.class)
						.in(Collect::getPostId, list)
		);
		List<CountDto> collectCount = collectMapper.getCollectCount(list);
		for (CountDto collectCountDto : collectCount) {
			System.out.println(collectCountDto);
		}

	}

}
