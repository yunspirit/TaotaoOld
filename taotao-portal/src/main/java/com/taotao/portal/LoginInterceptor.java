package com.taotao.portal;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
//    注意父子容器不能扫描问题
    @Value("${SSO_LOGIN}")
    private  String SSO_LOGIN;

    //1、拦截请求url
    //2、从cookie中取token
//3、如果没有toke跳转到登录页面。
//4、取到token，需要调用sso系统的服务查询用户信息。
//5、如果用户session已经过期，跳转到登录页面
//6、如果没有过期，放行
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        TbUser user=userService.getUserByToken(httpServletRequest,httpServletResponse);
//        用户信息过期
        if(user==null){
            System.out.println("跨域拦截");
            //当前请求的URL
            httpServletRequest.getSession();//增加这行代码用来解决问题
            System.out.println(httpServletRequest.getRequestURL());
            httpServletResponse.sendRedirect(SSO_LOGIN+"?redirectURL="+httpServletRequest.getRequestURL());
        }
//        没有过期 放行
        httpServletRequest.setAttribute("user",user);
        System.out.println("本地拦截");
        return true;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }


    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
