package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import com.somecoder.demo.blog.service.IPostService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 帖子表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@RequestMapping("/blog/post")
@Api(tags = "首页文章相关")
public class PostController {

    @Resource
    private IPostService postService;

    @PostMapping("/writePost")
    @ApiOperation("写帖子")
    public ApiResponse<Object> writePost(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @Validated @RequestBody WritePostRequest writePostRequest
    ) {
        postService.writePost(writePostRequest, loginUser);
        return ApiResponse.success();
    }

    @PostMapping("/uploadPic")
    @ApiOperation("写文章上传图片")
    public ApiResponse<UploadPicResponse> uploadPic(@RequestPart MultipartFile multipartFile) {
        try {
            UploadPicResponse uploadPicResponse = postService.uploadPic(multipartFile);
            return ApiResponse.success(uploadPicResponse);
        } catch (IOException e) {
            return ApiResponse.error("上传失败");
        }
    }

    @PostMapping("/searchPost")
    @ApiOperation("搜索相关文章")
    public ApiResponse<Page<GetPostListResponse>> searchPost(
            @RequestBody SearchPostRequest searchPostRequest
    ) {
        return ApiResponse.success(postService.searchPost(searchPostRequest));
    }

    @PostMapping("/getPostDetail")
    @ApiOperation("获取文章详情")
    public ApiResponse<GetPostDetailResponse> getPostDetail(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @Validated @RequestBody GetPostDetailRequest getPostDetailRequest
    ) {
        GetPostDetailResponse getPostDetailResponse = postService.getPostDetail(
                loginUser, getPostDetailRequest
        );
        return ApiResponse.success(getPostDetailResponse);
    }

    @PostMapping("/getMyPostList")
    @ApiOperation("获取我的帖子列表")
    public ApiResponse<Page<GetMyPostListResponse>> getMyPostList(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @RequestBody GetMyPostListRequest getMyPostListRequest
    ) {
        Page<GetMyPostListResponse> getMyPostListResponsePage =
                postService.getMyPostList(loginUser.getUserId(), getMyPostListRequest);
        return ApiResponse.success(getMyPostListResponsePage);
    }

    @PostMapping("/getThePostList")
    @ApiOperation("获取作者帖子列表")
    public ApiResponse<Page<GetThePostListResponse>> getThePostList(
            @RequestBody GetThePostListRequest getThePostListRequest
    ) {
        Page<GetThePostListResponse> getThePostListResponsePage = postService.getThePostList(getThePostListRequest);
        return ApiResponse.success(getThePostListResponsePage);
    }

    @PostMapping("/getAuthorInformation")
    @ApiOperation("获取作者信息")
    public ApiResponse<GetAuthorInformationResponse> getAuthorInformation(
            @RequestBody GetAuthorInformationRequest getAuthorInformationRequest
    ) {
        GetAuthorInformationResponse getAuthorInformationResponse
                = postService.getAuthorInformation(getAuthorInformationRequest);
        return ApiResponse.success(getAuthorInformationResponse);
    }

    @PostMapping("/getCount")
    @ApiOperation("获取获赞数和文章数和获评数")
    public ApiResponse<GetCountResponse> getCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser
    ) {
        GetCountResponse getCountResponse = postService.getCount(loginUser);
        return ApiResponse.success(getCountResponse);
    }

    @PostMapping("/modifyPost")
    @ApiOperation("修改帖子")
    public ApiResponse<Object> modifyPost(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @Validated @RequestBody ModifyPostRequest modifyPostRequest
    ) {
        postService.modifyPost(loginUser.getUserId(), modifyPostRequest);
        return ApiResponse.success();
    }

    @PostMapping("/deleteMyPost")
    @ApiOperation("删除帖子(学生端)")
    public ApiResponse<Object> deleteMyPost(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @RequestBody DeleteMyPostRequest deleteMyPostRequest
    ) {
        postService.deleteMyPost(loginUser.getUserId(), deleteMyPostRequest);
        return ApiResponse.success();
    }

    @PostMapping("/deletePost")
    @ApiOperation("删除帖子(管理员端)")
    public ApiResponse<Object> deletePost(
            @RequestBody DeletePostRequest deletePostRequest
    ) {
        postService.deletePost(deletePostRequest);
        return ApiResponse.success();
    }

    @PostMapping("/top")
    @ApiOperation("置顶")
    public ApiResponse<Object> top(
            @RequestBody TopRequest topRequest
    ) {
        postService.top(topRequest);
        return ApiResponse.success();
    }

    @PostMapping("/cancelTop")
    @ApiOperation("取消置顶")
    public ApiResponse<Object> cancelTop(
            @RequestBody TopRequest topRequest
    ) {
        postService.cancelTop(topRequest);
        return ApiResponse.success();
    }

    @PostMapping("/search")
    @ApiOperation("管理员帖子模糊查询")
    public ApiResponse<Page<ThePostListResponse>> search(
            @RequestBody SearchRequest searchRequest
    ) {
        Page<ThePostListResponse> getPostListResponsePage = postService.search(searchRequest);
        return ApiResponse.success(getPostListResponsePage);
    }
}
