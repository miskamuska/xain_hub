package com.somecoder.demo.blog.entity.dto;

import lombok.Data;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/19 10:44
 * @description：TODO
 */
@Data
public class CountDto {
    private int count;
    private String postId;

    @Override
    public String toString() {
        return "CollectCountDto{" +
                "count=" + count +
                ", postId='" + postId + '\'' +
                '}';
    }
}
