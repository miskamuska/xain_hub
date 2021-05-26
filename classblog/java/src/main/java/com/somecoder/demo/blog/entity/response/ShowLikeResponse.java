package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("显示点赞响应")
public class ShowLikeResponse {

    @ApiModelProperty(value = "姓名")
    private List<String> name;

    @ApiModelProperty(value = "点赞人数")
    private Integer likeCount;
}
