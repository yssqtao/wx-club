package com.yssq.subject.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yssq.subject.application.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * TODO 自定义 Spring MVC 的 JSON 序列化和反序列化行为，目的是：响应时忽略null值
 */
@Configuration
public class GlobalConfig extends WebMvcConfigurationSupport {

    // 在处理 HTTP 请求和响应时，Spring MVC 会优先使用自定义的 JSON 转换器
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(mappingJackson2HttpMessageConverter());
    }

    /**
     * TODO 题目模块开发完成后，对 jackson converter 处理
     * 自定义mappingJackson2HttpMessageConverter
     * 用于处理json格式的请求和响应
     * 目前实现：空值忽略，空字段可返回
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO 1.避免序列化空的 Java Bean（没有字段或字段都为 null）时抛出异常。
        //默认情况下，如果一个对象没有任何属性，Jackson 会抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //TODO 2.序列化时忽略值为 null 的字段，响应数据中不会包含 null 字段，可通过ApiPost工具测试
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**");
    }
}
