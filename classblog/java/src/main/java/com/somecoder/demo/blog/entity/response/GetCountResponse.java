package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("获赞数和文章数")
public class GetCountResponse {

    @ApiModelProperty("获赞数")
    private int gainUpvote;

    @ApiModelProperty("文章数")
    private int postCount;

    @ApiModelProperty("获评数")
    private int commentCount;
}
