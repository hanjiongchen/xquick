package com.nb6868.xquick.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * mybatis-plus配置
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置分页
     * see {https://mybatis.plus/guide/page.html}
     *
     * @return PaginationInterceptor
     */
    @Bean
    @Order(0)
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        return new PaginationInterceptor();
    }

}
