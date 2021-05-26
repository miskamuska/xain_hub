package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.Message;
import com.somecoder.demo.blog.entity.Post;
import com.somecoder.demo.blog.entity.Upvote;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.CancelTheUpvoteRequest;
import com.somecoder.demo.blog.entity.request.GetMyUpvoteListRequest;
import com.somecoder.demo.blog.entity.request.ShowLikeRequest;
import com.somecoder.demo.blog.entity.request.UpvoteRequest;
import com.somecoder.demo.blog.entity.response.GetMyUpvoteResponse;
import com.somecoder.demo.blog.entity.response.ShowLikeResponse;
import com.somecoder.demo.blog.entity.response.UpvoteResponse;
import com.somecoder.demo.blog.mapper.MessageMapper;
import com.somecoder.demo.blog.mapper.PostMapper;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.mapper.UpvoteMapper;
import com.somecoder.demo.blog.service.IUpvoteService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class UpvoteServiceImpl extends ServiceImpl<UpvoteMapper, Upvote> implements IUpvoteService {

    @Resource
    StudentMapper studentMapper;

    @Resource
    UpvoteMapper upvoteMapper;

    @Resource
    PostMapper postMapper;

    @Resource
    MessageMapper messageMapper;

    /**
     *
     * 点赞
     */
    @Override
    public UpvoteResponse likePost(String userId, UpvoteRequest upvoteRequest) {
        // 将点赞信息插入点赞表中
        Upvote theUpvote = upvoteMapper.selectOne(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, upvoteRequest.getPostId())
                        .eq(Upvote::getUserId, userId)
        );
        if (theUpvote != null) {
            throw new CustomException("不可重复点赞");
        }
        Upvote likePost = new Upvote();
        likePost.setPostId(upvoteRequest.getPostId());
        likePost.setUserId(userId);
        upvoteMapper.insert(likePost);
        // 点赞消息插入消息表中
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, upvoteRequest.getPostId())
        );
        if (!userId.equals(post.getUserId())) {
            Message message = new Message();
            message.setMessageId(PrefixConstant.MESSAGE_PREFIX + UUID.randomUUID());
            message.setPostId(upvoteRequest.getPostId());
            message.setFromUserId(userId);
            message.setToUserId(post.getUserId());
            message.setOperation("点赞");
            messageMapper.insert(message);
        }
        // 点赞总人数
        int likeCount = upvoteMapper.selectCount(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, upvoteRequest.getPostId())
        );
        UpvoteResponse upvoteResponse = new UpvoteResponse();
        upvoteResponse.setLikeCount(likeCount);
        return upvoteResponse;
    }

    /**
     *
     * 取消点赞
     */
    @Override
    public void cancelTheUpvote(String userId, CancelTheUpvoteRequest cancelTheUpvoteRequest) {
        Upvote theUpvote = upvoteMapper.selectOne(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, cancelTheUpvoteRequest.getPostId())
                        .eq(Upvote::getUserId, userId)
        );
        if (theUpvote == null) {
            throw new CustomException("未找到此帖子");
        }
        // 在点赞表中删除数据
        upvoteMapper.delete(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, cancelTheUpvoteRequest.getPostId())
                        .eq(Upvote::getUserId, userId)
        );
        messageMapper.delete(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getPostId, cancelTheUpvoteRequest.getPostId())
                        .eq(Message::getFromUserId, userId)
        );
    }

    /**
     *
     * 显示点赞信息
     */
    @Override
    public ShowLikeResponse showLike(ShowLikeRequest showLikeRequest) {
        ShowLikeResponse showLikeResponse = new ShowLikeResponse();
        List<String> allUpvote = upvoteMapper.getAllUpvote(showLikeRequest.getPostId());
        showLikeResponse.setName(allUpvote);
        showLikeResponse.setLikeCount(allUpvote.size());
        return showLikeResponse;
    }

    /**
     *
     * 获取我的点赞列表
     */
    @Override
    public Page<GetMyUpvoteResponse> getMyUpvoteList(String userId, GetMyUpvoteListRequest getMyUpvoteListRequest) {
        // 分页查询
        LambdaQueryWrapper<Upvote> upvoteListQuery = Wrappers.lambdaQuery(Upvote.class).eq(Upvote::getUserId, userId);
        Page<Upvote> upvoteQueryPage = new Page<>();
        BeanUtils.copyProperties(getMyUpvoteListRequest.getPageRequest(), upvoteQueryPage);
        Page<Upvote> upvotePage = upvoteMapper.selectPage(upvoteQueryPage, upvoteListQuery);
        List<Upvote> upvoteList = upvotePage.getRecords();
        Page<GetMyUpvoteResponse> getMyUpvoteResponsePage = new Page<>();
        BeanUtils.copyProperties(upvotePage, getMyUpvoteResponsePage);

        List<GetMyUpvoteResponse> getMyUpvoteResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取点赞列表
        for (Upvote upvote : upvoteList) {
            GetMyUpvoteResponse getMyUpvoteResponse = new GetMyUpvoteResponse();
            Post post = postMapper.selectOne(
                    Wrappers.lambdaQuery(Post.class)
                            .eq(Post::getPostId, upvote.getPostId())
            );
            BeanUtils.copyProperties(post, getMyUpvoteResponse);
            getMyUpvoteResponse.setName(studentMapper.getStudentName(post.getUserId()));
            getMyUpvoteResponse.setUpdateTime(dtf.format(post.getUpdateTime()));
            getMyUpvoteResponseList.add(getMyUpvoteResponse);
        }
        getMyUpvoteResponsePage.setRecords(getMyUpvoteResponseList);
        return  getMyUpvoteResponsePage;
    }
}
