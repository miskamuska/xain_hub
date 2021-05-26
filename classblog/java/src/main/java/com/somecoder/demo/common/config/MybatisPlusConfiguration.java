package com.somecoder.demo.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 * @author zuozhiwei
 */
@Configuration
@MapperScan("com.somecoder.demo.*.mapper")
public class MybatisPlusConfiguration {
    /**
     * 注册mybatis-plus插件
     * @return  注册器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 指定数据库类型可优化速度，避免每次查询数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
