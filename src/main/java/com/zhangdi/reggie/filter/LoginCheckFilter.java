package com.zhangdi.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.zhangdi.reggie.common.BaseContext;
import com.zhangdi.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        //1.获取本次请求uri
        String requestURI = request.getRequestURI();
        log.info("Request uri:{}",requestURI);
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送请求
                "/user/login"//移动端登录

        };
        //2.判断本次请求是否需要过滤（某些请求及路径可以直接放行）
        boolean check = check(urls,requestURI);

        //3.不需要处理，直接放行
        if(check){
            log.info("This Request doesn't need to filter:{}",requestURI);
            filterChain.doFilter(request,response);//直接放行
            return;
        }

        //4-1.判断登录状态，如果已经登录，直接放行（不能直接放行的请求，检查是否已登录）
        if(request.getSession().getAttribute("employee")!=null){
            log.info("User logged in, user ID is:{}",request.getSession().getAttribute("employee"));

            Long empId = (long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            //long id = Thread.currentThread().getId();
            //log.info("Thread Id is:{}",id);

            filterChain.doFilter(request,response);//直接放行
            return;
        }

        //4-2.判断用户登录状态，如果已经登录，直接放行（不能直接放行的请求，检查是否已登录）
        if(request.getSession().getAttribute("user")!=null){
            log.info("User logged in, user ID is:{}",request.getSession().getAttribute("user"));

            Long userId = (long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            //long id = Thread.currentThread().getId();
            //log.info("Thread Id is:{}",id);

            filterChain.doFilter(request,response);//直接放行
            return;
        }

        //5.如果未登录返回未登录结果，通过输出流的方式向客户端页面响应数据
        log.info("User not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;



    }

    /**
     * 检查本次请求是否需要处理
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }

        }
        return false;
    }


}

