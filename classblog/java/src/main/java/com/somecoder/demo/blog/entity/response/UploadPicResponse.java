package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("上传图片响应")
public class UploadPicResponse {

    @ApiModelProperty(value = "图片地址")
    private String src;

    @ApiModelProperty(value = "图片名")
    private String title;
}
