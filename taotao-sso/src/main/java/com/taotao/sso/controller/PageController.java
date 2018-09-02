package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

@Controller
public class PageController {
    //用于跳转到注册页面
    @RequestMapping("/page/register")
    public String showRegister(){
        System.out.println("我不信没有拦截到");
        return "register";
    }

    //用于跳转到登录页面
    @RequestMapping("/page/login")
    public String showLogin(String redirectURL, Model model){
        model.addAttribute("redirect",redirectURL);
        return "login";
    }
}
