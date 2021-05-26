package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.DeleteMessageRequest;
import com.somecoder.demo.blog.entity.request.GetCollectMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetCommentMessageListRequest;
import com.somecoder.demo.blog.entity.request.GetUpvoteMessageListRequest;
import com.somecoder.demo.blog.entity.response.GetCollectMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetCommentMessageListResponse;
import com.somecoder.demo.blog.entity.response.GetUpvoteMessageListResponse;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;
import com.somecoder.demo.blog.service.IMessageService;
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
 * 消息表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@RequestMapping("/blog/message")
@Api(tags = "消息相关")
public class MessageController {

    @Resource
    private IMessageService messageService;


    @PostMapping("/getUpvoteMessageList")
    @ApiOperation("点赞信息列表")
    public ApiResponse<Page<GetUpvoteMessageListResponse>> getUpvoteMessageList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody GetUpvoteMessageListRequest getUpvoteMessageListRequest
    ) {
        Page<GetUpvoteMessageListResponse> getUpvoteMessageList =
                messageService.getUpvoteMessageList(loginUser.getUserId(), getUpvoteMessageListRequest);
        return ApiResponse.success(getUpvoteMessageList);
    }

    @PostMapping("/getCollectMessageList")
    @ApiOperation("收藏信息列表")
    public ApiResponse<Page<GetCollectMessageListResponse>> getCollectMessageList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody GetCollectMessageListRequest getCollectMessageListRequest
    ) {
        Page<GetCollectMessageListResponse> getCollectMessageList =
                messageService.getCollectMessageList(loginUser.getUserId(), getCollectMessageListRequest);
        return ApiResponse.success(getCollectMessageList);
    }

    @PostMapping("/getCommentMessageList")
    @ApiOperation("评论和回复信息列表")
    public ApiResponse<Page<GetCommentMessageListResponse>> getCommentMessageList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody GetCommentMessageListRequest getCommentMessageListRequest
    ) {
        Page<GetCommentMessageListResponse> getCommentMessageList =
                messageService.getCommentMessageList(loginUser.getUserId(), getCommentMessageListRequest);
        return ApiResponse.success(getCommentMessageList);
    }

    @PostMapping("/deleteMessage")
    @ApiOperation("删除消息")
    public ApiResponse<Object> deleteMessage(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody DeleteMessageRequest deleteMessageRequest
    ) {
        messageService.deleteMessage(loginUser.getUserId(), deleteMessageRequest);
        return ApiResponse.success();
    }

    @PostMapping("/messageCount")
    @ApiOperation("未读消息总数")
    public ApiResponse<MessageCountResponse> messageCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        MessageCountResponse messageCountResponse = messageService.countMessage(loginUser.getUserId());
        return ApiResponse.success(messageCountResponse);
    }

    @PostMapping("/commentAndReplyCount")
    @ApiOperation("未读评论和回复消息数")
    public ApiResponse<MessageCountResponse> commentAndReplyCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        MessageCountResponse messageCountResponse = messageService.countCommentAndReply(loginUser.getUserId());
        return ApiResponse.success(messageCountResponse);
    }

    @PostMapping("/upvoteCount")
    @ApiOperation("点赞消息数")
    public ApiResponse<MessageCountResponse> upvoteCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        MessageCountResponse messageCountResponse = messageService.countUpvote(loginUser.getUserId());
        return ApiResponse.success(messageCountResponse);
    }

    @PostMapping("/collectCount")
    @ApiOperation("收藏消息数")
    public ApiResponse<MessageCountResponse> collectCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        MessageCountResponse messageCountResponse = messageService.countCollect(loginUser.getUserId());
        return ApiResponse.success(messageCountResponse);
    }
    @PostMapping("/clearCommentAndReplyCount")
    @ApiOperation("清空评论和回复消息数")
    public ApiResponse<Object> clearCommentAndReplyCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        messageService.clearCommentAndReplyCount(loginUser.getUserId());
        return ApiResponse.success();
    }

    @PostMapping("/clearUpvoteCount")
    @ApiOperation("清空点赞消息数")
    public ApiResponse<Object> clearUpvoteCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        messageService.clearUpvoteCount(loginUser.getUserId());
        return ApiResponse.success();
    }

    @PostMapping("/clearCollectCount")
    @ApiOperation("清空收藏消息数")
    public ApiResponse<Object> clearCollectCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        messageService.clearCollectCount(loginUser.getUserId());
        return ApiResponse.success();
    }


}
