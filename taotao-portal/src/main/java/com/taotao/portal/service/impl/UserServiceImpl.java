package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

//    SSO_BASE_URL=http://localhost:8084
//            #SSO验证token的URL
//    SSO_USER_TOKEN_SERVICE=/user/token/
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;
    //在浏览器cookie中取得token  然后查询用户信息
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token=CookieUtils.getCookieValue(request,"TT_TOKEN");
            if(StringUtils.isBlank(token)){
                return null;
            }
            System.out.println("token不为空");
            String json=HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN_SERVICE+token);

            //json转换为java对象
            TaotaoResult result=TaotaoResult.format(json);
//        判断是否过期
            if(result.getStatus()!=200){
                return null;
            }
            result=TaotaoResult.formatToPojo(json,TbUser.class);
            TbUser user=(TbUser) result.getData();
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
