package com.example.eliteedu_prism.EliteEdu_Prism.config;


import com.example.eliteedu_prism.EliteEdu_Prism.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration//注解表明是配置类
public class Config implements WebMvcConfigurer {


    @Autowired
    private Interceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/login");//添加拦截器对象,并指定拦截的资源路径
        //在拦截器中“/**”表示拦截所有资源，
        //“/login”表示除了这个路径，拦截其他所有
    }
}
