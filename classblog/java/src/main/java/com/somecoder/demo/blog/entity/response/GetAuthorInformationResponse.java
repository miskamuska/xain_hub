package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取作者信息响应")
public class GetAuthorInformationResponse {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty("获赞数")
    private int gainUpvote;

    @ApiModelProperty("文章数")
    private int postCount;

    @ApiModelProperty("获评数")
    private int commentCount;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;
}
