package com.imooc.mall;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by 廖师兄
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserLoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/error", "/user/login", "/user/register", "/categories", "/products", "/products/*");
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {

		return new PaginationInterceptor();

	}

}
