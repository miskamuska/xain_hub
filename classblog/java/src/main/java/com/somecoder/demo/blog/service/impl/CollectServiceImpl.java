package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.*;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddCollectRequest;
import com.somecoder.demo.blog.entity.request.PostListRequest;
import com.somecoder.demo.blog.entity.response.GetMyCollectListResponse;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.ICollectService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private UpvoteMapper upvoteMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private MessageMapper messageMapper;


    /**
     *
     * 添加收藏
     */
    @Override
    public void addCollect(AddCollectRequest addCollectRequest, LoginUser loginUser) {
        // 收藏
        int count = collectMapper.selectCount(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, addCollectRequest.getPostId())
                        .eq(Collect::getUserId, loginUser.getUserId())
        );
        if (count > 0) {
            throw new CustomException(1, "您已经收藏该文章");
        }
        Collect collect = new Collect();
        collect.setPostId(addCollectRequest.getPostId());
        collect.setUserId(loginUser.getUserId());
        collectMapper.insert(collect);
        // 收藏消息
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, addCollectRequest.getPostId())
        );
        if (!post.getUserId().equals(loginUser.getUserId())) {
            Message message = new Message();
            message.setMessageId(PrefixConstant.MESSAGE_PREFIX + UUID.randomUUID());
            message.setPostId(addCollectRequest.getPostId());
            message.setFromUserId(loginUser.getUserId());
            message.setToUserId(post.getUserId());
            message.setOperation("收藏");
            messageMapper.insert(message);
        }
    }

    /**
     *
     * 收藏数
     */
    @Override
    public int showCountCollect(AddCollectRequest addCollectRequest) {
        return collectMapper.selectCount(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, addCollectRequest.getPostId())
        );
    }

    /**
     *
     * 显示我的收藏列表
     */
    @Override
    public List<GetMyCollectListResponse> showMyCollect(PostListRequest postListRequest, LoginUser loginUser) {
        List<Collect> collectList = collectMapper.selectList(
                Wrappers.lambdaQuery(Collect.class)
                        .orderByDesc(Collect::getUpdateTime)
                        .eq(Collect::getUserId, loginUser.getUserId())
        );
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<GetMyCollectListResponse> list = new LinkedList<>();
        for (long i = (postListRequest.getCurrent() - 1) * postListRequest.getSize(), size = collectList.size();
             i < postListRequest.getCurrent() * postListRequest.getSize() && i < size;
             i++
        ) {
            GetMyCollectListResponse getPostListResponse = new GetMyCollectListResponse();
            Post post = postMapper.selectOne(
                    Wrappers.lambdaQuery(Post.class)
                            .eq(Post::getPostId, collectList.get((int)i).getPostId())
            );
            BeanUtils.copyProperties(post, getPostListResponse);
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, post.getUserId())
            );

            getPostListResponse.setUpvoteCount(
                    upvoteMapper.selectCount(
                            Wrappers.lambdaQuery(Upvote.class)
                                    .eq(Upvote::getPostId, collectList.get((int)i).getPostId())
                    )
            );

            getPostListResponse.setCollectCount(
                    collectMapper.selectCount(
                            Wrappers.lambdaQuery(Collect.class)
                                    .eq(Collect::getPostId, collectList.get((int)i).getPostId())
                    )
            );

            getPostListResponse.setCommentCount(
                    commentMapper.selectCount(
                            Wrappers.lambdaQuery(Comment.class)
                                    .eq(Comment::getPostId, collectList.get((int)i).getPostId())
                    )
            );

            getPostListResponse.setName(student == null ? "匿名" : student.getName());
            getPostListResponse.setIsTop(post.getIsTop());
            getPostListResponse.setUpdateTime(dtf.format(post.getUpdateTime()));
            getPostListResponse.setCurrent(postListRequest.getCurrent());
            getPostListResponse.setSize(postListRequest.getSize());
            getPostListResponse.setPages((long)(Math.ceil(1.0 * size / postListRequest.getSize())));
            getPostListResponse.setTotal(size);
            getPostListResponse.setPostContent(HtmlUtils.htmlUnescape(post.getPostContent()));
            list.add(getPostListResponse);
        }
        return list;
    }

    /**
     *
     * 删除收藏
     */
    @Override
    public void deleteCollect(AddCollectRequest addCollectRequest, LoginUser loginUser) {
        int count = collectMapper.selectCount(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, addCollectRequest.getPostId())
                        .eq(Collect::getUserId, loginUser.getUserId())
        );
        if (count == 0) {
            throw new CustomException(1, "您还未收藏该文章");
        }
        collectMapper.delete(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, addCollectRequest.getPostId())
                        .eq(Collect::getUserId, loginUser.getUserId())
        );
        messageMapper.delete(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getPostId, addCollectRequest.getPostId())
                        .eq(Message::getFromUserId, loginUser.getUserId())
        );
    }
}
