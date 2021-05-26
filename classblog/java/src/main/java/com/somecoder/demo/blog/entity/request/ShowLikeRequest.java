package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("显示点赞实体类")
public class ShowLikeRequest {

    @ApiModelProperty(value = "帖子主键")
    private String postId;

}
