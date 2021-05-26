package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("管理员文章列表响应")
public class ThePostListResponse {

    @ApiModelProperty(value = "帖子主键")
    private String postId;

    @ApiModelProperty(value = "作者姓名")
    private String name;

    @ApiModelProperty(value = "文章标题")
    private String postTheme;

    @ApiModelProperty(value = "文章分类")
    private String sortTheme;

    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "文章发布时间")
    private String createTime;
}
