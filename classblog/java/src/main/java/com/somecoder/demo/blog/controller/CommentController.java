package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.AddCommentRequest;
import com.somecoder.demo.blog.entity.request.DeleteCommentRequest;
import com.somecoder.demo.blog.entity.request.ShowCommentRequest;
import com.somecoder.demo.blog.entity.response.ShowCommentResponse;
import com.somecoder.demo.blog.service.ICommentService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@AccessToken
@Api(tags = "评论相关")
@CrossOrigin
@RestController
@RequestMapping("/blog/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @ApiOperation("评论")
    @PostMapping("/addComment")
    public ApiResponse<Object> addComment(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody AddCommentRequest addCommentRequest
    ) {
        commentService.addComment(loginUser, addCommentRequest);
        return ApiResponse.success();
    }

    @ApiOperation("显示评论")
    @PostMapping("/showComment")
    public ApiResponse<List<ShowCommentResponse>> showComment(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @Validated @RequestBody ShowCommentRequest showCommentRequest
    ) {
        List<ShowCommentResponse> list = commentService.showComment(loginUser, showCommentRequest);
        return ApiResponse.success(list);
    }

    @ApiOperation("删除评论")
    @PostMapping("/deleteComment")
    public ApiResponse<Object> deleteComment(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody DeleteCommentRequest deleteCommentRequest
    ) {
        commentService.deleteComment(loginUser, deleteCommentRequest);
        return ApiResponse.success();
    }
}
