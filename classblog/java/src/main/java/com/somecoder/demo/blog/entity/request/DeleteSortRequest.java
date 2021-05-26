package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("删除分类实体类")
public class DeleteSortRequest {

    @ApiModelProperty(value = "分类主键")
    private String sortId;
}
