package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.*;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.dto.PostListQueryDto;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.ICommentService;
import com.somecoder.demo.blog.service.IPostService;
import com.somecoder.demo.blog.service.IUpvoteService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Value("${file.url}")
    private String url;

    @Resource
    private PostMapper postMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UpvoteMapper upvoteMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private ICommentService commentService;

    @Resource
    private IUpvoteService upvoteService;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private SortMapper sortMapper;

    /**
     *
     * 写帖子
     */
    @Override
    public void writePost(WritePostRequest writePostRequest, LoginUser loginUser) {
        writePostRequest.setPostContent(HtmlUtils.htmlEscape(writePostRequest.getPostContent()));
        Sort sort = sortMapper.selectOne(
                Wrappers.lambdaQuery(Sort.class)
                        .eq(Sort::getSortTheme, writePostRequest.getSortTheme())
        );
        Post post = new Post();
        BeanUtils.copyProperties(writePostRequest, post);
        post.setUserId(loginUser.getUserId());
        post.setPostId(PrefixConstant.POST_PREFIX + UUID.randomUUID());
        post.setSortId(sort.getSortId());
        postMapper.insert(post);
    }

    @Override
    public Page<GetPostListResponse> searchPost(SearchPostRequest searchPostRequest) {
        Page<Post> postQueryPage = new Page<>();
        BeanUtils.copyProperties(searchPostRequest.getPostListRequest(), postQueryPage);
        PostListQueryDto postListQueryDto = new PostListQueryDto();
        BeanUtils.copyProperties(searchPostRequest, postListQueryDto);
        Page<GetPostListResponse> postList = postMapper.getPostList(postQueryPage, postListQueryDto);
        for (GetPostListResponse record : postList.getRecords()) {
            record.setPostContent(HtmlUtils.htmlUnescape(record.getPostContent()));
        }
        return postList;
    }

    /**
     *
     * 获取我的帖子列表
     */
    @Override
    public Page<GetMyPostListResponse> getMyPostList(
            String userId, GetMyPostListRequest getMyPostListRequest
    ) {
        // 模糊搜索帖子标题
        LambdaQueryWrapper<Post> postListQuery =
                Wrappers.lambdaQuery(Post.class).eq(Post::getUserId, userId);
        if (StringUtils.isNotBlank(getMyPostListRequest.getKeyword())) {
            postListQuery.like(Post::getPostTheme, getMyPostListRequest.getKeyword());
        }
        // 分页查询
        Page<Post> postListQueryPage = new Page<>();
        BeanUtils.copyProperties(getMyPostListRequest.getPageRequest(), postListQueryPage);
        Page<Post> postPage = postMapper.selectPage(postListQueryPage, postListQuery);
        List<Post> postList = postPage.getRecords();

        Page<GetMyPostListResponse> getMyPostListResponsePage = new Page<>();
        BeanUtils.copyProperties(postPage, getMyPostListResponsePage);
        List<GetMyPostListResponse> getPostListResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 帖子列表
        postList.forEach(post -> {
            post.setPostContent(HtmlUtils.htmlUnescape(post.getPostContent()));
            GetMyPostListResponse getMyPostListResponse = new GetMyPostListResponse();
            if (post.getUserId().equals(userId)) {
                BeanUtils.copyProperties(post, getMyPostListResponse);
            }
            int upvoteCount = upvoteMapper.selectCount(
                    Wrappers.lambdaQuery(Upvote.class)
                            .eq(Upvote::getPostId, post.getPostId())
            );
            int commentCount = commentMapper.selectCount(
                    Wrappers.lambdaQuery(Comment.class)
                            .eq(Comment::getPostId, post.getPostId())
            );
            Sort sort = sortMapper.selectOne(
                    Wrappers.lambdaQuery(Sort.class)
                            .eq(Sort::getSortId, post.getSortId())
            );
            getMyPostListResponse.setName(studentMapper.getStudentName(userId));
            getMyPostListResponse.setCreateTime(dtf.format(post.getCreateTime()));
            getMyPostListResponse.setUpvoteCount(upvoteCount);
            getMyPostListResponse.setCommentCount(commentCount);
            getMyPostListResponse.setSortTheme(sort.getSortTheme());
            getPostListResponseList.add(getMyPostListResponse);
        });
        getMyPostListResponsePage.setRecords(getPostListResponseList);
        return getMyPostListResponsePage;
    }

    /**
     *
     * 修改帖子分类
     */
    @Override
    public void modifyPost(String userId, ModifyPostRequest modifyPostRequest) {
        if (StringUtils.isNotBlank(modifyPostRequest.getSortTheme())) {
            Post post = postMapper.selectOne(
                    Wrappers.lambdaQuery(Post.class)
                            .eq(Post::getPostId, modifyPostRequest.getPostId())
            );
            if (post == null) {
                throw new CustomException("该帖子不存在");
            }
            Sort sort = sortMapper.selectOne(
                    Wrappers.lambdaQuery(Sort.class)
                            .eq(Sort::getSortTheme, modifyPostRequest.getSortTheme())
            );
            post.setSortId(sort.getSortId());
            postMapper.updateById(post);
        }
    }

    /**
     *
     * 删除我的帖子（学生端）
     */
    @Override
    public void deleteMyPost(String userId, DeleteMyPostRequest deleteMyPostRequest) {
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getUserId, userId)
                        .eq(Post::getPostId, deleteMyPostRequest.getPostId())
        );
        if (post == null) {
            throw new CustomException("该帖子不存在");
        }
        postMapper.delete(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getUserId, userId)
                        .eq(Post::getPostId, deleteMyPostRequest.getPostId())
        );
        upvoteMapper.delete(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, deleteMyPostRequest.getPostId())
        );
        collectMapper.delete(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, deleteMyPostRequest.getPostId())
        );
    }

    /**
     *
     * 删除帖子（管理员端）
     */
    @Override
    public void deletePost(DeletePostRequest deletePostRequest) {
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, deletePostRequest.getPostId())
        );
        if (post == null) {
            throw new CustomException("该帖子不存在");
        }
        postMapper.delete(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, deletePostRequest.getPostId())
        );
        upvoteMapper.delete(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getPostId, deletePostRequest.getPostId())
        );
        collectMapper.delete(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getPostId, deletePostRequest.getPostId())
        );
    }

    /**
     *
     * 置顶
     */
    @Override
    public void top(TopRequest topRequest) {
        // 查看置顶帖子条数
        int maxCount = 5;
        int topCount = postMapper.selectCount(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getIsTop, 1)
        );
        if (topCount < maxCount) {
            Post post = postMapper.selectOne(
                    Wrappers.lambdaQuery(Post.class)
                            .eq(Post::getPostId, topRequest.getPostId())
            );
            post.setIsTop(true);
            postMapper.updateById(post);
        } else {
            throw new CustomException("置顶的帖子不能超过5条");
        }
    }


    @Override
    public GetPostDetailResponse getPostDetail(
            LoginUser loginUser, GetPostDetailRequest getPostDetailRequest
    ) {
        GetPostDetailResponse postDetail = postMapper.getPostDetail(getPostDetailRequest.getPostId());
        postDetail.setPostContent(HtmlUtils.htmlUnescape(postDetail.getPostContent()));

        ShowLikeRequest showLikeRequest = new ShowLikeRequest();
        showLikeRequest.setPostId(getPostDetailRequest.getPostId());
        postDetail.setShowLikeResponse(upvoteService.showLike(showLikeRequest));

        ShowCommentRequest showCommentRequest = new ShowCommentRequest();
        showCommentRequest.setPostId(getPostDetailRequest.getPostId());
        List<ShowCommentResponse> showCommentResponses = commentService.showComment(
                loginUser, showCommentRequest
        );
        postDetail.setCommentList(showCommentResponses);
        postDetail.setCommentCount(showCommentResponses.size());
        // 我是否点赞
        postDetail.setIsUpvote(
                upvoteMapper.selectOne(
                        Wrappers.lambdaQuery(Upvote.class)
                                .eq(Upvote::getPostId, getPostDetailRequest.getPostId())
                                .eq(Upvote::getUserId, loginUser.getUserId())
                ) != null
        );
        // 收藏人数
        postDetail.setCollectCount(
                collectMapper.selectCount(
                        Wrappers.lambdaQuery(Collect.class)
                                .eq(Collect::getPostId, getPostDetailRequest.getPostId())
                )
        );
        // 我是否收藏
        postDetail.setIsCollect(
                collectMapper.selectOne(
                        Wrappers.lambdaQuery(Collect.class)
                                .eq(Collect::getUserId, loginUser.getUserId())
                                .eq(Collect::getPostId, getPostDetailRequest.getPostId())
                ) != null
        );
        return postDetail;
    }

    /**
     *
     * 获取评论、点赞、收藏数
     */
    @Override
    public GetCountResponse getCount(LoginUser loginUser) {
        GetCountResponse getCountResponse = new GetCountResponse();
        List<Post> posts = postMapper.selectList(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getUserId, loginUser.getUserId())
        );
        getCountResponse.setPostCount(posts.size());
        int sumUpvote = 0;
        int sumComment = 0;
        for (Post post : posts) {
            sumUpvote += upvoteMapper.selectCount(
                    Wrappers.lambdaQuery(Upvote.class)
                            .eq(Upvote::getPostId, post.getPostId())
            );
            sumComment += commentMapper.selectCount(
                    Wrappers.lambdaQuery(Comment.class)
                            .eq(Comment::getPostId, post.getPostId())
            );
        }
        getCountResponse.setGainUpvote(sumUpvote);
        getCountResponse.setCommentCount(sumComment);
        return getCountResponse;
    }

    /**
     *
     * 取消置顶
     */
    @Override
    public void cancelTop(TopRequest topRequest) {
        Post post = postMapper.selectOne(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getPostId, topRequest.getPostId())
        );
        if (!post.getIsTop()) {
            throw new CustomException(1, "该文章未置顶");
        }
        post.setIsTop(false);
        postMapper.updateById(post);
    }

    /**
     *
     * 获取作者帖子列表
     */
    @Override
    public Page<GetThePostListResponse> getThePostList(GetThePostListRequest getThePostListRequest) {
        LambdaQueryWrapper<Post> postListQuery =
                Wrappers.lambdaQuery(Post.class).eq(Post::getUserId, getThePostListRequest.getUserId());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 分页查询
        Page<Post> postListQueryPage = new Page<>();
        BeanUtils.copyProperties(getThePostListRequest.getPageRequest(), postListQueryPage);
        Page<Post> postPage = postMapper.selectPage(postListQueryPage, postListQuery);
        List<Post> postList = postPage.getRecords();

        Page<GetThePostListResponse> getThePostListResponsePage = new Page<>();
        BeanUtils.copyProperties(postPage, getThePostListResponsePage);
        List<GetThePostListResponse> getPostListResponseList = new ArrayList<>();
        // 帖子列表
        for (Post post : postList) {
            GetThePostListResponse getThePostListResponse = new GetThePostListResponse();
            if (post.getUserId().equals(getThePostListRequest.getUserId())) {
                BeanUtils.copyProperties(post, getThePostListResponse);
            }
            int upvoteCount = upvoteMapper.selectCount(
                    Wrappers.lambdaQuery(Upvote.class)
                            .eq(Upvote::getPostId, post.getPostId())
            );
            int commentCount = commentMapper.selectCount(
                    Wrappers.lambdaQuery(Comment.class)
                            .eq(Comment::getPostId, post.getPostId())
            );
            getThePostListResponse.setSortTheme(sortMapper.getSortTheme(post.getSortId()));
            getThePostListResponse.setCreateTime(dtf.format(post.getCreateTime()));
            getThePostListResponse.setName(studentMapper.getStudentName(getThePostListRequest.getUserId()));
            getThePostListResponse.setUpvoteCount(upvoteCount);
            getThePostListResponse.setCommentCount(commentCount);
            getThePostListResponse.setPostContent(HtmlUtils.htmlUnescape(post.getPostContent()));
            getPostListResponseList.add(getThePostListResponse);
        }
        getThePostListResponsePage.setRecords(getPostListResponseList);
        return getThePostListResponsePage;
    }

    /**
     *
     * 获取作者信息
     */
    @Override
    public GetAuthorInformationResponse getAuthorInformation(
            GetAuthorInformationRequest getAuthorInformationRequest
    ) {
        GetAuthorInformationResponse getAuthorInformationResponse = new GetAuthorInformationResponse();
        // 帖子数
        List<Post> postList = postMapper.selectList(
                Wrappers.lambdaQuery(Post.class)
                        .eq(Post::getUserId, getAuthorInformationRequest.getUserId())
        );
        getAuthorInformationResponse.setPostCount(postList.size());
        // 点赞数和收藏数
        int sumUpvote = 0;
        int sumComment = 0;
        for (Post post : postList) {
            sumUpvote += upvoteMapper.selectCount(
                    Wrappers.lambdaQuery(Upvote.class)
                            .eq(Upvote::getPostId, post.getPostId())
            );
            sumComment += commentMapper.selectCount(
                    Wrappers.lambdaQuery(Comment.class)
                            .eq(Comment::getPostId, post.getPostId())
            );
        }
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, getAuthorInformationRequest.getUserId())
        );
        getAuthorInformationResponse.setKey("url");
        getAuthorInformationResponse.setValue(student.getHeadPicUrl());
        getAuthorInformationResponse.setName(
                studentMapper.getStudentName(getAuthorInformationRequest.getUserId())
        );
        getAuthorInformationResponse.setGainUpvote(sumUpvote);
        getAuthorInformationResponse.setCommentCount(sumComment);
        return getAuthorInformationResponse;
    }

    /**
     *
     * 上传相片
     */
    @Override
    public UploadPicResponse uploadPic(MultipartFile file) throws IOException {
        // 获取工作目录
        String currentPath = System.getProperty("user.dir");
        // 如果不是定位到java目录，就添加路径
        if (!currentPath.endsWith("java")) {
            currentPath += "/java";
        }
        currentPath += "/upload/";

        String fileName = UUID.randomUUID().toString().replace("-", "");
        Path path = Paths.get(currentPath, fileName + ".jpg");
        file.transferTo(path);
        UploadPicResponse uploadPicResponse = new UploadPicResponse();
        uploadPicResponse.setSrc(url + fileName + ".jpg");
        uploadPicResponse.setTitle(fileName + ".jpg");
        return uploadPicResponse;
    }

    /**
     *
     * 管理员页面帖子列表
     */
    @Override
    public Page<ThePostListResponse> search(SearchRequest searchRequest) {
        // 模糊查询帖子主题
        LambdaQueryWrapper<Post> postListQuery =
                Wrappers.lambdaQuery(Post.class).orderByDesc(Post::getIsTop).orderByDesc(Post::getCreateTime);
        if (StringUtils.isNotBlank(searchRequest.getPostTheme())) {
            postListQuery.like(Post::getPostTheme, searchRequest.getPostTheme());
        }
        // 分页
        Page<Post> postListQueryPage = new Page<>();
        BeanUtils.copyProperties(searchRequest.getPostListRequest(), postListQueryPage);
        Page<Post> postPage = postMapper.selectPage(postListQueryPage, postListQuery);
        List<Post> postList = postPage.getRecords();

        Page<ThePostListResponse> getMyPostListResponsePage = new Page<>();
        BeanUtils.copyProperties(postPage, getMyPostListResponsePage);
        List<ThePostListResponse> getPostListResponseList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 帖子列表
        postList.forEach(post -> {
            ThePostListResponse thePostListResponse = new ThePostListResponse();
            BeanUtils.copyProperties(post, thePostListResponse);
            thePostListResponse.setPostTheme(post.getPostTheme());
            thePostListResponse.setCreateTime(dtf.format(post.getCreateTime()));
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, post.getUserId())
            );
            thePostListResponse.setName(student.getName());
            Sort sort = sortMapper.selectOne(
                    Wrappers.lambdaQuery(Sort.class)
                            .eq(Sort::getSortId, post.getSortId())
            );
            thePostListResponse.setSortTheme(sort.getSortTheme());
            getPostListResponseList.add(thePostListResponse);
        });
        getMyPostListResponsePage.setRecords(getPostListResponseList);
        return getMyPostListResponsePage;
    }
}
