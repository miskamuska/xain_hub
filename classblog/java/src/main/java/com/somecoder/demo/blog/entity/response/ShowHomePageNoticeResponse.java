package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @since 2021-01-28
 */
@Data
@ApiModel("首页显示公告标题响应类")
public class ShowHomePageNoticeResponse {

    @ApiModelProperty("公告主键")
    private String noticeId;

    @ApiModelProperty("发布年月")
    private String yearMonth;

    @ApiModelProperty("发布日")
    private String day;

    @ApiModelProperty("公告标题")
    private String noticeTheme;
}
