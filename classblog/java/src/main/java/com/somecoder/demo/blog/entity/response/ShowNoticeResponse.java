package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("显示公告响应")
public class ShowNoticeResponse {

    @ApiModelProperty(value = "公告主键")
    private String noticeId;

    @ApiModelProperty(value = "发布人")
    private String name;

    @ApiModelProperty(value = "公告标题")
    private String noticeTheme;

    @ApiModelProperty(value = "公告内容")
    private String noticeContent;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
