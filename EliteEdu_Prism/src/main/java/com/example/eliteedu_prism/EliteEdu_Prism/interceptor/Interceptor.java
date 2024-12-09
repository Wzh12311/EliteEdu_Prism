package com.example.eliteedu_prism.EliteEdu_Prism.interceptor;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Component
//拦截器（）
/**
 * 拦截器，需要验证token拦截不合法用户
 */
public class Interceptor implements HandlerInterceptor
{




    @Override//拦截前运行  在此处做登录校验操作
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        // filterChain.doFilter(servletRequest,servletResponse);//放行操作

//        HttpServletRequest req= (HttpServletRequest) servletRequest;
//        HttpServletResponse resp= (HttpServletResponse) servletResponse;

        //此时没有必要强转了，因为prehandle传进来的就是HttpServletRequest类的

        //获取请求的url
        String url=req.getRequestURI();
        System.out.println(url);

        //判断url是否含有login关键字
        if(url.contains("login")||url.contains("swagger-ui")||url.contains("swagger-config")||url.contains("v3/api-docs")){

//            filterChain.doFilter(servletRequest,servletResponse);//放行操作


            return true;  //返回true表示放行
        }//如果包含则放行

        //如果不是登录操作，需要获取令牌jwt(token)
        String token = req.getHeader("token");
        //判断令牌是否存在
//        if(token==null||token.equals("")){
//            //不存在则跳转到登录页面
//            resp.sendRedirect("/login.html");
//            return;
//        }

        if(!StringUtils.hasLength(token))
        {
            log.info("token不存在,返回未登录的信息");

            Result notLogin = Result.error("NOT_LOGIN");
            //将对象转换成json格式的字符串,因为在controller中，会自动将返回值转化为json，而过滤器中不会

            String json = new ObjectMapper().writeValueAsString(notLogin);
            resp.getWriter().write(json);
            return false;//如果令牌不存在，那么不能放行

        }
        //如果jwt存在，那么校验令牌，如果合理则放行
        try {
            JwtUtils.parseJwt(token);//如果报错，说明解析失败，令牌不合理
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("token解析失败,返回未登录的信息");

            Result notLogin = Result.error("NOT_LOGIN");
            //将对象转换成json格式的字符串,因为在controller中，会自动将返回值转化为json，而过滤器中不会

            String json = new ObjectMapper().writeValueAsString(notLogin);
            resp.getWriter().write(json);
            return false;//令牌不合理也不能放行
        }

        //存在则放行
//        filterChain.doFilter(servletRequest,servletResponse);

        return true;
    }

    @Override//拦截后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override//最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
