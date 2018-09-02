package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

//var b = "http://localhost:8082/search.html?q="+encodeURIComponent(document.getElementById(a).value);
@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
//本地搜索拦截 然后调用searchService获取solr中的关键字 并返回结果参数 填入到结果页面中
   @RequestMapping("/search")
   public String search(@RequestParam(value = "q") String keyword,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "30") int rows, Model model){
       System.out.println("我被拦截");
       try {
           keyword=new String(keyword.getBytes("iso8859-1"),"utf-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
           keyword="";
       }
       SearchResult searchResult=searchService.search(keyword,page,rows);
       model.addAttribute("query",keyword);
       model.addAttribute("totalPages",searchResult.getPageCount());
       model.addAttribute("itemList",searchResult.getItemList());
       model.addAttribute("page",searchResult.getCurPage());
       return "search";
   }
}
