package com.taotao.sso.httpclient;

import com.taotao.portal.pojo.CartItem;
import freemarker.template.Template;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestCart {
    @Test
    public static void main(String[] args) {
        List<CartItem> list=new LinkedList<>();
        list.add(new CartItem());
        System.out.println(list.get(0).getPrice());


        for (CartItem item:list){
            item.setPrice((long) 100);
        }
        System.out.println(list.get(0).getPrice());
    }
}
