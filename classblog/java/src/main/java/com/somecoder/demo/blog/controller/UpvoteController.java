package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.CancelTheUpvoteRequest;
import com.somecoder.demo.blog.entity.request.GetMyUpvoteListRequest;
import com.somecoder.demo.blog.entity.request.ShowLikeRequest;
import com.somecoder.demo.blog.entity.request.UpvoteRequest;
import com.somecoder.demo.blog.entity.response.GetMyUpvoteResponse;
import com.somecoder.demo.blog.entity.response.ShowLikeResponse;
import com.somecoder.demo.blog.entity.response.UpvoteResponse;
import com.somecoder.demo.blog.service.IUpvoteService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@Api(tags = "点赞相关")
@RequestMapping("/blog/upvote")
public class UpvoteController {

    @Resource
    IUpvoteService upvoteService;

    @ApiOperation("显示点赞")
    @PostMapping("showUpvote")
    public ApiResponse<ShowLikeResponse> showLike(
            @RequestBody ShowLikeRequest showLikeRequest
    ) {
        ShowLikeResponse showLikeResponse = upvoteService.showLike(showLikeRequest);
        return ApiResponse.success(showLikeResponse);
    }

    @ApiOperation("点赞")
    @PostMapping("/upvote")
    public ApiResponse<UpvoteResponse> likePost(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody UpvoteRequest upvoteRequest
    ) {
        UpvoteResponse upvoteResponse = upvoteService.likePost(loginUser.getUserId(), upvoteRequest);
        return ApiResponse.success("点赞成功", upvoteResponse);
    }

    @ApiOperation("取消点赞")
    @PostMapping("/cancelTheUpvote")
    public ApiResponse<Object> cancelTheUpvote(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody CancelTheUpvoteRequest cancelTheUpvoteRequest
    ) {
        upvoteService.cancelTheUpvote(loginUser.getUserId(), cancelTheUpvoteRequest);
        return ApiResponse.success();
    }

    @ApiOperation("点赞列表")
    @PostMapping("/myUpvoteList")
    public ApiResponse<Page<GetMyUpvoteResponse>> getMyUpvoteList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody GetMyUpvoteListRequest getMyUpvoteListRequest
    ) {
        Page<GetMyUpvoteResponse> getMyUpvoteResponsePage;
        getMyUpvoteResponsePage = upvoteService.getMyUpvoteList(loginUser.getUserId(), getMyUpvoteListRequest);
        return ApiResponse.success(getMyUpvoteResponsePage);
    }
}
