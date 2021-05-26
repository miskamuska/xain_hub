package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.AddReplyRequest;
import com.somecoder.demo.blog.entity.response.ReplyIdResponse;
import com.somecoder.demo.blog.service.IReplyService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@CrossOrigin
@AccessToken
@Api(tags = "回复相关")
@RestController
@RequestMapping("/blog/reply")
public class ReplyController {

    @Resource
    private IReplyService replyService;

    @PostMapping("/addReply")
    @ApiOperation("回复")
    public ApiResponse<Object> addReply(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody AddReplyRequest addReplyRequest
    ) {
        replyService.addReply(loginUser, addReplyRequest);
        return ApiResponse.success();
    }

    @ApiOperation("删除回复")
    @PostMapping("/deleteReply")
    public ApiResponse<Object> deleteReply(
            @RequestBody ReplyIdResponse replyIdResponse
    ) {
        replyService.deleteReply(replyIdResponse);
        return ApiResponse.success();
    }

}
