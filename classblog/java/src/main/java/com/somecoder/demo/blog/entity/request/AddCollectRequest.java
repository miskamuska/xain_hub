package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("添加收藏实体类")
public class AddCollectRequest {

    @ApiModelProperty("帖子主键")
    private String postId;
}
