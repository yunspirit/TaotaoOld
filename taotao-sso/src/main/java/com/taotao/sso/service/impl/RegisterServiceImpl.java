package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    TbUserMapper tbUserMapper;

    //注册接口 数据校验
    public TaotaoResult checkData(String param, int type) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria=example.createCriteria();
        //1、2、3分别代表username、phone、email
        if(type==1){
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            criteria.andPhoneEqualTo(param);
        }else if(type==3){
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> list=tbUserMapper.selectByExample(example);

        if(list==null || list.size()==0) return TaotaoResult.ok(true);

        return TaotaoResult.ok(false);
    }

    //    注册接口 用户注册
    public TaotaoResult register(TbUser user) {
        if(StringUtils.isBlank(user.getUsername()) ||StringUtils.isBlank(user.getPassword()) ){
            return TaotaoResult.build(400,"用户名或密码不能为空");
        }

        TaotaoResult result=checkData(user.getUsername(),1);
        if(!(boolean)result.getData()){
             return TaotaoResult.build(400,"用户名重复");
        }

        if(user.getPhone()!=null){
            result=checkData(user.getPhone(),2);
            if((boolean)result.getData()==false){
                    return TaotaoResult.build(400,"手机号重复");
            }
        }

        if(user.getEmail()!=null){
            result=checkData(user.getEmail(),3);
            if((boolean)result.getData()==false){
                return TaotaoResult.build(400,"邮箱重复");
            }
        }

//        插入数据  还需要补全
        Date date=new Date();
        user.setCreated(date);
        user.setUpdated(date);

        //密码进行md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        tbUserMapper.insert(user);

        return TaotaoResult.ok();
    }



}
