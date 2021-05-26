package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.ShowFriendListResponse;
import com.somecoder.demo.blog.service.IFriendService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
@Api(tags = "私信好友相关")
@CrossOrigin
@Slf4j
@AccessToken
@RestController
@RequestMapping("/blog/friend")
public class FriendController {

    @Resource
    private IFriendService friendService;

    @ApiOperation("查看私信好友列表")
    @PostMapping("/showFriendList")
    public ApiResponse<List<ShowFriendListResponse>> showFriendList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        List<ShowFriendListResponse> list = friendService.showFriendList(loginUser);
        return ApiResponse.success(list);
    }

    @ApiOperation("删除会话")
    @PostMapping("/deleteFriend")
    public ApiResponse<Object> deleteFriend(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody ShowLetterRecordRequest showLetterRecordRequest
    ) {
        friendService.deleteFriend(loginUser, showLetterRecordRequest);
        return ApiResponse.success();

    }



}
