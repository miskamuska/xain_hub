package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("获取作者信息实体类")
public class GetAuthorInformationRequest {

    @ApiModelProperty("用户主键")
    private String userId;
}
