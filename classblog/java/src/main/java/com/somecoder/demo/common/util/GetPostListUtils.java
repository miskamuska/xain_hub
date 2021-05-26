package com.somecoder.demo.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.somecoder.demo.blog.entity.Post;
import com.somecoder.demo.blog.entity.response.GetPostListResponse;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.service.ICollectService;
import com.somecoder.demo.blog.service.ICommentService;
import com.somecoder.demo.blog.service.IUpvoteService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/3 10:20
 * @description：获取文章列表工具类，置顶的在前面，修改了时间格式，添加了姓名
 */
@Component
public class GetPostListUtils {

    @Resource
    private  IUpvoteService upvoteService;

    @Resource
    private  ICollectService collectService;

    @Resource
    private  ICommentService commentService;

    public static GetPostListUtils getPostListUtils;

    @PostConstruct
    private void init() {
        getPostListUtils = this;
    }

    public static List<GetPostListResponse> getPostList(IPage<Post> page, StudentMapper studentMapper) {


//        List<GetPostListResponse> listPostTopResponse = new LinkedList<>();
//        List<GetPostListResponse> listPostResponse = new LinkedList<>();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
//
//        AddCollectRequest addCollectRequest = new AddCollectRequest();
//        ShowCommentRequest showCommentRequest = new ShowCommentRequest();
//        ShowLikeRequest showLikeRequest = new ShowLikeRequest();
//
//
//        for (int i = 0, size = page.getRecords().size(); i < size; i++) {
//            GetPostListResponse getPostListResponse = new GetPostListResponse();
//            BeanUtils.copyProperties(page.getRecords().get(i), getPostListResponse);
//            Student student = studentMapper.selectOne(
//                    Wrappers.lambdaQuery(Student.class)
//                            .eq(Student::getUserId, page.getRecords().get(i).getUserId())
//            );
//            getPostListResponse.setName(student == null ? "匿名" : student.getName());
//            getPostListResponse.setTop(page.getRecords().get(i).getIsTop());
//            getPostListResponse.setUpdateTime(dtf.format(page.getRecords().get(i).getCreateTime()));
//            getPostListResponse.setCurrent(page.getCurrent());
//            getPostListResponse.setSize(page.getSize());
//            getPostListResponse.setPages(page.getPages());
//            getPostListResponse.setTotal(page.getTotal());
//
////            // 获取收藏数
//            addCollectRequest.setId(page.getRecords().get(i).getId());
//            int collectCount = getPostListUtils.collectService.showCountCollect(addCollectRequest);
//            getPostListResponse.setCollectCount(collectCount);
//
//            // 获取回复列表
//            showCommentRequest.setPostId(page.getRecords().get(i).getId());
//            List<ShowCommentResponse> commentList = getPostListUtils.commentService.showComment(showCommentRequest);
//            getPostListResponse.setCommentList(commentList);
////
//            // 获取点赞列表
//            showLikeRequest.setPostId(page.getRecords().get(i).getId());
//            ShowLikeResponse showLikeResponse = getPostListUtils.upvoteService.showLike(showLikeRequest);
//            getPostListResponse.setShowLikeResponse(showLikeResponse);
//
//
//
//            if (page.getRecords().get(i).getIsTop()) {
//                listPostTopResponse.add(getPostListResponse);
//            } else {
//                listPostResponse.add(getPostListResponse);
//            }
//        }
//        listPostTopResponse.addAll(listPostResponse);
//        return listPostTopResponse;
        return null;
    }

}
