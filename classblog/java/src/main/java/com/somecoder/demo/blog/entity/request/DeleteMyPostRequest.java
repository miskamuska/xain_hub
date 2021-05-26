package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除帖子实体类(学生端）")
public class DeleteMyPostRequest {

    @ApiModelProperty(value = "帖子主键")
    private String postId;
}
