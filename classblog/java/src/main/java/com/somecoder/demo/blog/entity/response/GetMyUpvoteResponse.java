package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021/1/28
 */
@Data
@ApiModel("获取点赞列表响应")
public class GetMyUpvoteResponse {

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "帖子主键")
    private Integer postId;

    @ApiModelProperty(value = "文章内容")
    private String postContent;

    @ApiModelProperty(value = "文章主题")
    private String postTheme;

    @ApiModelProperty(value = "文章分类")
    private String sortId;

    @ApiModelProperty(value = "文章最后更新时间")
    private String updateTime;
}
