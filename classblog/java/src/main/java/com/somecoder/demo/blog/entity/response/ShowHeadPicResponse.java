package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("显示头像响应")
public class ShowHeadPicResponse {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;

}
