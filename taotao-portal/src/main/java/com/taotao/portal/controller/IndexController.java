package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/index")
    public String showIndex(Model model){
        String json=contentService.getAd1tentList();
//        在index.jsp中  var data = ${ad1};  需要ad1属性
        model.addAttribute("ad1",json);
        return "index";
    }

    @RequestMapping(value = "/posttest",method = RequestMethod.POST)
    @ResponseBody
    public String postTest(String name ,String pass){
        System.out.println(name);
        System.out.println(pass);
        return "ok";
    }

//    @RequestMapping(value = "/posttest",method = RequestMethod.POST)
//    @ResponseBody
//    public String postTest(@RequestBody Map map){
//        System.out.println(map.get("name"));
//        System.out.println(map.get("pass"));
//        return "ok";
//    }
}
