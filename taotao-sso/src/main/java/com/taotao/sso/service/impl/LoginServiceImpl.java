package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.component.JedisClient;
import com.taotao.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private String SESSION_EXPIRE;

//    用户登录
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list=tbUserMapper.selectByExample(example);
        if(list==null || list.size()==0){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser user=list.get(0);
        //校验密码
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //生成token
        String token=UUID.randomUUID().toString();
        //放入redis  key:REDIS_SESSION:{TOKEN}   value:user转换成json
        user.setPassword(null);
        jedisClient.set(REDIS_SESSION_KEY+":"+token,JsonUtils.objectToJson(user));
//        设置redis过期时间
        jedisClient.expire(REDIS_SESSION_KEY+":"+token,Integer.parseInt(SESSION_EXPIRE));
        //写入cookie  默认是浏览器关闭才消除cookie  生存时间是以秒为单位
        CookieUtils.setCookie(request,response,"TT_TOKEN",token,Integer.parseInt(SESSION_EXPIRE));

        return TaotaoResult.ok(token);
    }

 //根据token获取用户信息
    public TaotaoResult getUserByToken(String token) {
        String json=jedisClient.get(REDIS_SESSION_KEY+":"+token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户信息已经过期");
        }
//        返回用户对象 此处是json字符串 需要转化
        TbUser user=JsonUtils.jsonToPojo(json,TbUser.class);
        jedisClient.expire(REDIS_SESSION_KEY+":"+token,Integer.parseInt(SESSION_EXPIRE));
        return TaotaoResult.ok(user);
    }
}
